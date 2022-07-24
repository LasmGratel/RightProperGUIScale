package com.github.basdxz.rightproperguiscale.mixin.mixins.client.minecraft;

import com.github.basdxz.rightproperguiscale.GUIJiggler;
import net.minecraft.client.gui.GuiVideoSettings;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.*;

import static com.github.basdxz.rightproperguiscale.GUIJiggler.mouseReleased;

@Mixin(GuiVideoSettings.class)
public abstract class GuiVideoSettingsMixin {
    @Inject(method = "mouseClicked(III)V",
            at = @At(value = "FIELD",
                     target = "net/minecraft/client/gui/GuiVideoSettings.guiGameSettings:" +
                              "Lnet/minecraft/client/settings/GameSettings;",
                     opcode = Opcodes.GETFIELD,
                     ordinal = 1,
                     by = -2),
            cancellable = true,
            require = 1)
    private void cancelScaledResolutionUpdate(int mousePosX, int mousePosY, int mouseButton, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "mouseMovedOrUp(III)V",
            at = @At(value = "FIELD",
                     target = "net/minecraft/client/gui/GuiVideoSettings.guiGameSettings:" +
                              "Lnet/minecraft/client/settings/GameSettings;",
                     opcode = Opcodes.GETFIELD,
                     ordinal = 1,
                     by = -2),
            cancellable = true,
            require = 1)
    private void redirectScaledResolutionUpdate(int mousePosX, int mousePosY, int mouseButton, CallbackInfo ci) {
        if (mouseReleased(mouseButton))
            GUIJiggler.updateGuiScale();
        ci.cancel();
    }
}