def convert_power_atlas(encoding_start_num: int) -> int: 

  output_file = "out/new_powers.atlas"
  input_file = "powers.atlas"

  encoding = encoding_start_num
  mapping = dict()

  with open(output_file, "w") as atlas_output_file:
    with open(input_file, "r") as atlas_input_file:
      for line in atlas_input_file:
        # Ignore small power icons (they are at the end of the file)
        if (line.startswith("48/")):
          break
        
        elif (line.startswith("128/")):
          # Get power name from file
          power_name = line[4:].strip()

          # Convert code point to a surrate pair (eg. F0000 => \uDB80\uDC0)
          enc = int_encoding_to_surrogate_pair(encoding)

          # Map name of power to surrogate pairing so people ctrl+f for power names
          # and copy paste encoding into translation file 
          mapping[power_name] = enc
          atlas_output_file.write(f"{encoding:x}\n")

          encoding += 1

        # Copy info of atlas into new atlas
        else:
          atlas_output_file.write(line)


  mapping_file = "out/power_name_map.txt"
  with open(mapping_file, "w") as map_file:
    # Tried to use json.dump but it replaced every '\' with '\\'
    sorted_dict = dict(sorted(mapping.items()))
    for k, v in sorted_dict.items():
      map_file.writelines(f"{k} => \"{v}\"\n")
  
  encoding -= 1
  return encoding

def int_encoding_to_surrogate_pair(code_point: int) -> str:
  hex_val = chr(code_point)
  encoded = hex_val.encode('utf-16-be')
  enc = f"\\u{pad_hex(2, encoded[0])}{pad_hex(2, encoded[1])}\\u{pad_hex(2, encoded[2])}{pad_hex(2, encoded[3])}"
  return enc

def pad_hex(padding: int, value: bytes) -> str:
  return f"{value:0{padding}X}"

convert_power_atlas(int("0xF0000", 16))