package EmojiMod.patches.com.megacrit.cardcrawl.helpers.ImageMaster;

import EmojiMod.EmojiMod;
import EmojiMod.EmojiSupport;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.helpers.ImageMaster;

@SpirePatch(clz = ImageMaster.class, method = "initialize")
public class ImageMasterPatches {

    public static void Postfix() {
        EmojiMod.logger.info("Loading emoji atlas");
        FileHandle newFileHandle = Gdx.files.internal(EmojiMod.getModID() + "Resources/images/emoji-support-multiple/emoji.atlas");
        EmojiMod.emojiSupport = new EmojiSupport();
        EmojiMod.emojiSupport.Load(newFileHandle);
        EmojiMod.logger.info("Completed loading emoji atlas");
    }
}
