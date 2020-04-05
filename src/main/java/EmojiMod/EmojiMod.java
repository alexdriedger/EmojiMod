package EmojiMod;

import EmojiMod.patches.com.megacrit.cardcrawl.core.Settings.SettingsPatch;
import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import EmojiMod.util.IDCheckDontTouchPls;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@SpireInitializer
public class EmojiMod implements
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostDungeonInitializeSubscriber
{
    
    public static final Logger logger = LogManager.getLogger(EmojiMod.class.getName());
    private static String modID;

    // TODO : Make texture atlas from emoji atlas. Intialize in ImageMaster initialize time
    public static TextureAtlas emojiAtlas;
    public static TextureAtlas.AtlasRegion testRegion;
    public static Map<String, TextureAtlas.AtlasRegion> cachedEmojis = new HashMap<>();

    // Format is U+{first code point}-{next code point}-{etc.}
    // Example: U+1F3C-200D-2642 for https://emojipedia.org/man-surfing/
    // Note: This should correspond to the code points defined on emojipedia
    // Note: U+FE0F is ignored in naming https://emojipedia.org/variation-selector-16/

    // TODO : Make a map of <emoji string identifier => AtlasRegion> so that the mod doesn't load all 2000+ emojis on start up and just uses the ones that are actually used
    // TODO : In `identifyOrb`, if the image isn't loaded into map, load it. else return texture from map
    // TODO : Figure out why sizing isn't working correctly

    public static EmojiSupport emojiSupport;
    
    public EmojiMod() {
        logger.info("Subscribe to BaseMod hooks");
        BaseMod.subscribe(this);
        setModID("EmojiMod");
        logger.info("Done subscribing");
    }
    
    public static void setModID(String ID) {
        Gson coolG = new Gson();
        
        InputStream in = EmojiMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json");
        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class);
        logger.info("You are attempting to set your mod ID as: " + ID);
        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) {
            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION);
        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) {
            modID = EXCEPTION_STRINGS.DEFAULTID;
        } else {
            modID = ID;
        }
        logger.info("Success! ID is " + modID);
    }
    
    public static String getModID() {
        return modID;
    }
    
    @SuppressWarnings("unused")
    public static void initialize() {
        logger.info("========================= Initializing Emoji Mod. =========================");
        EmojiMod defaultmod = new EmojiMod();
        logger.info("========================= /Emoji Mod Initialized. Hello :)./ =========================");
    }
    
    @Override
    public void receiveEditStrings() {
        logger.info("Beginning to edit strings for mod with ID: " + getModID());

        String path = getModID() + "Resources/localization/" + Settings.language.toString().toLowerCase() + "/";
        logger.info("receiveEditString path:\t" + path);

        BaseMod.loadCustomStringsFile(UIStrings.class,
                path + "EmojiMod-Language-Dropdown-Strings.json");

//        if (Settings.language == SettingsPatch.EMO) {
//            logger.info("Loading emoji localization files");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "achievements.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "blights.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "cards.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "characters.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "credits.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "events.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "keywords.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "monsters.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "orbs.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "potions.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "powers.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "relics.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "run_mods.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "score_bonuses.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "stances.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "tutorials.json");
//            BaseMod.loadCustomStringsFile(UIStrings.class,
//                    path + "ui.json");
//        }
        
        logger.info("Done editing strings");
    }
    
    @Override
    public void receiveEditKeywords() {
        
        
//        Gson gson = new Gson();
//        String json = Gdx.files.internal(getModID() + "Resources/localization/eng/DefaultMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
//        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);
//
//        if (keywords != null) {
//            for (Keyword keyword : keywords) {
//                BaseMod.addKeyword(getModID().toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
//            }
//        }
    }

    @Override
    public void receivePostDungeonInitialize() {
        logger.info("postDungeonInitialize");
        logger.info("Game Language is:\t" + Settings.language.toString());
    }

    public static String makeID(String idText) {
        return getModID() + ":" + idText;
    }
}
