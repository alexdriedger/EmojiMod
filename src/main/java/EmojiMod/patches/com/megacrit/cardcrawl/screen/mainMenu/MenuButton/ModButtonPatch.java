package EmojiMod.patches.com.megacrit.cardcrawl.screen.mainMenu.MenuButton;

import EmojiMod.EmojiMod;
import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.screens.mainMenu.MenuButton;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.megacrit.cardcrawl.screens.mainMenu.MenuButton.ClickResult.*;

public class ModButtonPatch {
    @SpirePatch(
            clz= MenuButton.class,
            method="setLabel"
    )
    public static class SetLabel
    {
        public static void Postfix(MenuButton __instance)
        {
            List<MenuButton.ClickResult> originalList = Arrays.asList(PLAY, RESUME_GAME, ABANDON_RUN, INFO, STAT, SETTINGS, PATCH_NOTES, QUIT);
            try {
                if (!originalList.contains(__instance.result)) {
                    EmojiMod.logger.info("Setting mod label");
                    Field f_label = MenuButton.class.getDeclaredField("label");
                    f_label.setAccessible(true);
                    f_label.set(__instance, "\uD83D\uDD27");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
