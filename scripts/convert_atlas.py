from typing import Dict, Tuple
from os import listdir
from os.path import isfile, join

def convert_power_atlas(encoding_start_num: int) -> Tuple[int, Dict[str, str]]: 
  # Converts the power atlas from the base game into an atlas usable by EmojiSupport

  output_file = "temp/new_powers.atlas"
  input_file = "in/powers.atlas"

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
  
  encoding -= 1
  return encoding, mapping

def convert_custom_atlas(encoding_start_num: int, input_file: str, output_file: str) -> Tuple[int, Dict[str, str]]:
  # Converts atlases created by the GDX texture packer into an atlas usable by EmojiSupport

  encoding = encoding_start_num
  mapping = dict()

  with open(output_file, "w") as atlas_output_file:
    with open(input_file, "r") as atlas_input_file:
      for index, line in enumerate(atlas_input_file):
        # Copy info of atlas into new atlas
        if line.startswith(" ") or index < 6:
          atlas_output_file.write(line)
        else:
          # Get power name from file
          icon_name = line.strip()

          # Convert code point to a surrate pair (eg. F0000 => \uDB80\uDC0)
          enc = int_encoding_to_surrogate_pair(encoding)

          # Map name of icon to surrogate pairing so people ctrl+f for icon names
          # and copy paste encoding into translation file 
          mapping[icon_name] = enc

          # Encode atlas with new code point for icon
          atlas_output_file.write(f"{encoding:x}\n")

          encoding += 1
  
  encoding -= 1
  return encoding, mapping

def convert_all_atlases() -> None:
  # Convert atlases into the format needed for EmojiSupport
  start_encoding = int("0xF0000", 16)
  encoding, mapping_dict = convert_power_atlas(start_encoding)
  encoding, relics_dict = convert_custom_atlas(encoding, input_file="in/sts_assets.atlas", output_file="temp/new_relics_blights.atlas")
  encoding, other_assets_dict = convert_custom_atlas(encoding, input_file="in/other_assets.atlas", output_file="temp/new_other_assets.atlas")
  encoding, x_a_dict = convert_custom_atlas(encoding, input_file="in/x_a.atlas", output_file="temp/x_a.atlas")

  # Merge all atlases into one atlas to copy into EmojiMod Resources
  update_main_atlas()

  # Create a file of custom emoji names to their mapping
  mapping_dict.update(relics_dict)
  mapping_dict.update(other_assets_dict)
  mapping_dict.update(x_a_dict)
  write_mapping_file(mapping_dict)

def update_main_atlas() -> None:
  input_dir = "temp/"
  emoji_atlas_path = "in/multiple.atlas"
  output_file_path = "out/emoji.atlas"
  
  with open(output_file_path, "w") as out_file:
    # Copy emoji atlas
    with open(emoji_atlas_path, "r") as emoji_file:
      out_file.write(emoji_file.read())

    # Custom generated atlases
    atlases = listdir(input_dir)
    atlases = [a for a in atlases if a.endswith(".atlas")]

    # Sort for consistency
    atlases.sort() 

    # Copy contents of each atlas into one atlas
    for a in atlases:
      atlas_path = input_dir + "/" + a
      with open(atlas_path, "r") as in_file:
        out_file.write(in_file.read())

def int_encoding_to_surrogate_pair(code_point: int) -> str:
  # Get the surrogate pair for a code point from the integer representation of the code point
  hex_val = chr(code_point)
  encoded = hex_val.encode('utf-16-be')
  enc = f"\\u{pad_hex(2, encoded[0])}{pad_hex(2, encoded[1])}\\u{pad_hex(2, encoded[2])}{pad_hex(2, encoded[3])}"
  return enc

def pad_hex(padding: int, value: bytes) -> str:
  return f"{value:0{padding}X}"

def write_mapping_file(mapping: Dict[str, str], file_name: str = "out/custom_emoji_mapping.txt") -> None:
  with open(file_name, "w") as map_file:
    # Tried to use json.dump but it replaced every '\' with '\\'
    sorted_dict = dict(sorted(mapping.items()))
    for k, v in sorted_dict.items():
      map_file.writelines(f"{k} => \"{v}\"\n")

convert_all_atlases()