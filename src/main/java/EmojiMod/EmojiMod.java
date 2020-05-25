package EmojiMod;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.ReflectionHacks;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.megacrit.cardcrawl.core.Settings.GameLanguage.ENG;

@SpireInitializer
public class EmojiMod implements
        EditStringsSubscriber,
        PostInitializeSubscriber
{

    // TODO: Determine best way to allow cross mod compatibility for adding emojis translations (and emojis?)
    // TODO: Set langPackDir (LocalizedStrings) to be english so that the emojis load even if the game language is not english
    
    public static final Logger logger = LogManager.getLogger(EmojiMod.class.getName());
    private static String modID;

    public static EmojiSupport emojiSupport;

    private static ReplaceData[] cardDescriptionWords;
    private static ReplaceData[] numberRP;
    private static ReplaceData[] eventDescriptionWords;
    private static ReplaceData[] eventOptionWords;
    private static ReplaceData[] uiWords;
    private static Map<String, CardStrings> replacementCardNames;
    private static Map<String, CardStrings> replacementCardNameAndDescriptions;
    
    public EmojiMod() {
        modID = "EmojiMod";
        logger.info(modID + " subscribing to BaseMod hooks");
        BaseMod.subscribe(this);
        logger.info(modID + " done subscribing");
    }
    
    public static String getModID() {
        return modID;
    }
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Emoji Mod :) :D :0 =========================");
        EmojiMod emojiMod = new EmojiMod();
        logger.info("========================= Emoji Mod Initialized. Hello :) =========================");
    }

    private static String loadJson(String path) {
        return Gdx.files.internal(path).readString(String.valueOf(StandardCharsets.UTF_8));
    }

    @Override
    public void receivePostInitialize() {
        BaseMod.registerModBadge(ImageMaster.loadImage("EmojiModResources/images/modBadge.png"), "Emoji Mod", "alexdriedger", "Emojis Everywhere", new ModPanel());
    }

    @Override
    public void receiveEditStrings() {
        try {
            String lang;
            if (Settings.language == ENG) {
                lang = Settings.language.name().toLowerCase();
            } else {
                logger.error("Emoji Mod only supports english");
                return;
            }

            String regexPath = "EmojiModResources/localization/" + lang + "/regex/";
            String replacePath = "EmojiModResources/localization/" + lang + "/replacement/";

            Gson gson = new Gson();
            cardDescriptionWords = gson.fromJson(loadJson(regexPath + "CardDescriptionPrimary.json"), ReplaceData[].class);
            numberRP = gson.fromJson(loadJson(regexPath + "Numbers.json"), ReplaceData[].class);
            eventDescriptionWords = gson.fromJson(loadJson(regexPath + "EventDescriptionImportant.json"), ReplaceData[].class);
            eventOptionWords = gson.fromJson(loadJson(regexPath + "EventOptionImportant.json"), ReplaceData[].class);
            uiWords = gson.fromJson(loadJson(regexPath + "UIStrings.json"), ReplaceData[].class);

            Type cardType = (new TypeToken<Map<String, CardStrings>>() {  }).getType();
            replacementCardNames = gson.fromJson(loadJson(replacePath + "CardNames.json"), cardType);
            replacementCardNameAndDescriptions = gson.fromJson(loadJson(replacePath + "CardNameAndDescriptions.json"), cardType);
        }
        catch (JsonSyntaxException e) {
            logger.error("Failed to load regex strings for emoji translation from json.");
        }
    }

    public static void PostLoadLocalizationStrings() {
        logger.info("Improving strings.");
        if (Settings.language == ENG) {
            EnglishImproveStrings();
        } else {
            logger.error("Unsupported language for EmojiMod. English must be loaded as the language");
        }
        logger.info("Finished improving strings");
    }

    /**
     * Replaces any parts of card strings in the base game with replacements.
     * This is used to provide direct translations for card names and descriptions when regex does not cover it
     * @param original CardStrings in the base game
     * @param replacements CardStrings to replace base game CardStrings
     */
    private static void replaceCardString(Map<String, CardStrings> original, Map<String, CardStrings> replacements) {
        for (Map.Entry<String, CardStrings> r : replacements.entrySet()) {
            if (!original.containsKey(r.getKey())) {
                original.put(r.getKey(), r.getValue());
            } else {
                CardStrings origCS = original.get(r.getKey());
                CardStrings replaceCS = r.getValue();

                if (replaceCS.NAME != null) {
                    origCS.NAME = replaceCS.NAME;
                }
                if (replaceCS.DESCRIPTION != null) {
                    origCS.DESCRIPTION = replaceCS.DESCRIPTION;
                }
                if (replaceCS.UPGRADE_DESCRIPTION != null) {
                    origCS.UPGRADE_DESCRIPTION = replaceCS.UPGRADE_DESCRIPTION;
                }
                if (replaceCS.EXTENDED_DESCRIPTION != null) {
                    origCS.EXTENDED_DESCRIPTION = replaceCS.EXTENDED_DESCRIPTION;
                }
            }
        }
    }

    private static void replaceMainMenuString() {
        Map<String, UIStrings> uiStrings = (Map<String, UIStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "ui");
        UIStrings menuStrings = uiStrings.get("MenuButton");
        menuStrings.TEXT = new String[] {
                "",
                "▶️",
                "",
                "",
                "⏩",
                "",
                "\uD83D\uDCC8",
                "",
                "❌",
                "\uD83D\uDCDD",
                "☠️",
                "",
                "⚙️",
                "",
                "\uD83D\uDCDA"
        };
    }

    @SuppressWarnings("unchecked")
    private static void EnglishImproveStrings() {
        Map<String, CardStrings> cardStrings = (Map<String, CardStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "cards");
        replaceCardString(cardStrings, replacementCardNames);
        replaceCardString(cardStrings, replacementCardNameAndDescriptions);
        changeStrings(cardStrings, cardDescriptionWords, "cards");

        replaceMainMenuString();
        Map<String, UIStrings> uiStrings = (Map<String, UIStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "ui");
        changeStrings(uiStrings, uiWords, "ui");
        changeStrings(uiStrings, cardDescriptionWords, "ui");

        Map<String, PowerStrings> powerStrings = (Map<String, PowerStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "powers");
        changeStrings(powerStrings, cardDescriptionWords, "powers");

        Map<String, EventStrings> eventStrings = (Map<String, EventStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "events");
        changeStrings(eventStrings, cardDescriptionWords, "events");

//        Map<String, RelicStrings> relicStrings = (Map<String, RelicStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "relics");
//        changeStrings(relicStrings, cardDescriptionWords, "relics");
//
//        Map<String, PotionStrings> potionStrings = (Map<String, PotionStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "potions");
//        changeStrings(potionStrings, cardDescriptionWords, "potions");
    }

    private static <V> void changeStrings(Map<String, V> stringMap, ReplaceData[] rd, String fieldName) {
        if (stringMap != null) {
            for (V strings : stringMap.values()) {
                EnglishHeckStrings(strings, rd);
            }
            ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, fieldName, stringMap);
        }
    }

    private static void EnglishHeckStrings(Object o, ReplaceData[] rd) {
        try {
            for (Field f : o.getClass().getDeclaredFields()) {
                if (f != null && f.getType().equals(String[].class)) {
                    String[] strings = (String[]) f.get(o);
                    if (strings != null) {
                        String[] returnVal = EnglishDestroyString(strings, rd);
                        f.setAccessible(true);
                        f.set(o, returnVal);
                    }
                } else if (f != null && f.getType().equals(String.class)) {
                    String s = (String) f.get(o);
                    if (s != null) {
                        String returnVal = EnglishDestroyString(s, rd);
                        f.setAccessible(true);
                        f.set(o, returnVal);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static String[] EnglishDestroyString(String[] spireString, ReplaceData[] regexReplacements) {
        for (int i = 0; i < spireString.length; i++) {
            if (spireString[i] != null) {
                spireString[i] = EnglishDestroyString(spireString[i], regexReplacements);
            }
        }
        return spireString;
    }

    private static String EnglishDestroyString(String spireString, ReplaceData[] regexReplacements)
    {
        String returnString = spireString;
        for (ReplaceData data : regexReplacements) {
            for (String phrase : data.KEYS) {
                if (data.VALUE == null) {
                    data.VALUE = "";
                }
                String replacement = returnString.replaceAll(phrase, data.VALUE);
                if (replacement.contains("ShortenTheSpireSpecialValue")) {
                    Matcher matches = Pattern.compile(phrase).matcher(returnString);
                    while (matches.find()) {
                        replacement = replacement.replaceFirst("ShortenTheSpireSpecialValue", matches.group(data.SPECIAL));
                    }
                }
                if (replacement.contains("ShortenTheSpireMix")) {
                    Matcher matches = Pattern.compile(phrase).matcher(returnString);
                    if (matches.find()) {
                        String replacementReplacement = "";
                        for (int i : data.REORGANIZE) {
                            replacementReplacement = replacementReplacement.concat(matches.group(i));
                        }
                        replacement = replacement.replace("ShortenTheSpireMix", replacementReplacement);
                    }
                }
                if (replacement.contains("ShortenTheSpireCapitalize")) {
                    Matcher matches = Pattern.compile(phrase).matcher(returnString);
                    if (matches.find()) {
                        replacement = replacement.replace("ShortenTheSpireCapitalize", matches.group(1).toUpperCase());
                    }
                }
                returnString = replacement;
            }
        }

        return returnString;
    }
}
