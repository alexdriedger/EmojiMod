package EmojiMod;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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

@SpireInitializer
public class EmojiMod implements
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        PostDungeonInitializeSubscriber
{
    
    public static final Logger logger = LogManager.getLogger(EmojiMod.class.getName());
    private static String modID;
    
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
        
//        BaseMod.loadCustomStringsFile(CardStrings.class,
//                getModID() + "Resources/localization/eng/DefaultMod-Card-Strings.json");
//
//
//        BaseMod.loadCustomStringsFile(PowerStrings.class,
//                getModID() + "Resources/localization/eng/DefaultMod-Power-Strings.json");
//
//
//        BaseMod.loadCustomStringsFile(RelicStrings.class,
//                getModID() + "Resources/localization/eng/DefaultMod-Relic-Strings.json");
//
//
//        BaseMod.loadCustomStringsFile(EventStrings.class,
//                getModID() + "Resources/localization/eng/DefaultMod-Event-Strings.json");
//
//
//        BaseMod.loadCustomStringsFile(PotionStrings.class,
//                getModID() + "Resources/localization/eng/DefaultMod-Potion-Strings.json");
//
//
//        BaseMod.loadCustomStringsFile(CharacterStrings.class,
//                getModID() + "Resources/localization/eng/DefaultMod-Character-Strings.json");
//
//
//        BaseMod.loadCustomStringsFile(OrbStrings.class,
//                getModID() + "Resources/localization/eng/DefaultMod-Orb-Strings.json");
        
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
