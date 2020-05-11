package EmojiMod;

import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import EmojiMod.util.IDCheckDontTouchPls;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.megacrit.cardcrawl.core.Settings.GameLanguage.ENG;

@SpireInitializer
public class EmojiMod implements
        EditStringsSubscriber
{

    // TODO: Determine best way to allow cross mod compatibility for adding emojis translations (and emojis?)
    // TODO: Do first pass of translations
    // TODO: Set langPackDir (LocalizedStrings) to be english so that the emojis load even if the game language is not english
    // TODO: Get better phrases for:
        /*
        for combat
        if
        when
        Innate
        copy of this card
        start of your turn
        end of your turn
         */
    
    public static final Logger logger = LogManager.getLogger(EmojiMod.class.getName());
    private static String modID;

    public static EmojiSupport emojiSupport;

    private static ReplaceData[] cardWords;
    private static ReplaceData[] eventDescriptionWords;
    private static ReplaceData[] eventOptionWords;
    private static Map<String, CardStrings> replacementCards;
    
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
            cardWords = gson.fromJson(loadJson(regexPath + "CardImportant.json"), ReplaceData[].class);
            eventDescriptionWords = gson.fromJson(loadJson(regexPath + "EventDescriptionImportant.json"), ReplaceData[].class);
            eventOptionWords = gson.fromJson(loadJson(regexPath + "EventOptionImportant.json"), ReplaceData[].class);

            Type cardType = (new TypeToken<Map<String, CardStrings>>() {  }).getType();
            replacementCards = gson.fromJson(loadJson(replacePath + "cards.json"), cardType);
        }
        catch (JsonSyntaxException e) {
            logger.error("Failed to load regex strings for emoji translation from json.");
        }
    }

    public static void PostLoadLocalizationStrings(LocalizedStrings localizedStrings) {
        logger.info("Improving strings.");
        if (Settings.language == ENG) {
            EnglishImproveStrings(localizedStrings);
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

    @SuppressWarnings("unchecked")
    private static void EnglishImproveStrings(LocalizedStrings localizedStrings) {
        try {
            Map<String, CardStrings> cardStrings = (Map<String, CardStrings>) ReflectionHacks.getPrivateStatic(LocalizedStrings.class, "cards");
            replaceCardString(cardStrings, replacementCards);
            if (cardStrings != null) {
                for (CardStrings cardString : cardStrings.values()) {
                    EnglishHeckStrings(cardString, cardWords);
                }

                ReflectionHacks.setPrivateStaticFinal(LocalizedStrings.class, "cards", cardStrings);
            }
        }
        catch (Exception e) {
            logger.error("Error while hecking strings - " + e.getMessage());
        }
    }

    private static void EnglishHeckStrings(CardStrings cardStrings, ReplaceData[] rd)
    {
        if (cardStrings.DESCRIPTION != null)
            cardStrings.DESCRIPTION = EnglishDestroyString(cardStrings.DESCRIPTION, rd);

        if (cardStrings.UPGRADE_DESCRIPTION != null)
            cardStrings.UPGRADE_DESCRIPTION = EnglishDestroyString(cardStrings.UPGRADE_DESCRIPTION, rd);

        if (cardStrings.EXTENDED_DESCRIPTION != null)
            for (int i = 0; i < cardStrings.EXTENDED_DESCRIPTION.length; i++)
                cardStrings.EXTENDED_DESCRIPTION[i] = EnglishDestroyString(cardStrings.EXTENDED_DESCRIPTION[i], rd);
    }
    private static void EnglishHeckStrings(EventStrings eventStrings)
    {
        if (eventStrings.DESCRIPTIONS != null)
            for (int i = 0; i < eventStrings.DESCRIPTIONS.length; i++)
                eventStrings.DESCRIPTIONS[i] = EnglishDestroyString(eventStrings.DESCRIPTIONS[i], eventDescriptionWords);

        if (eventStrings.OPTIONS != null)
            for (int i = 0; i < eventStrings.OPTIONS.length; i++)
                eventStrings.OPTIONS[i] = EnglishDestroyString(eventStrings.OPTIONS[i], eventOptionWords);
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
