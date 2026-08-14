package hub.lol.menumusic.mixins;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.minecraft.sounds.SoundEvents.MUSIC_MENU;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.SoundEngine;
import net.minecraft.client.sounds.SoundManager;

@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @Shadow
    @Final
    private SoundEngine soundEngine;

    @Inject(at = @At("HEAD"), method = "play(Lnet/minecraft/client/resources/sounds/SoundInstance;)Lnet/minecraft/client/sounds/SoundEngine$PlayResult;", cancellable = true)
    private void injectPlayDirect(SoundInstance sound, CallbackInfoReturnable<SoundEngine.PlayResult> ci) {
        if (sound == null) {
            return;
        }
        if (sound.getIdentifier().equals(MUSIC_MENU.key().identifier())) {
            ci.setReturnValue(SoundEngine.PlayResult.NOT_STARTED);
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "playDelayed(Lnet/minecraft/client/resources/sounds/SoundInstance;I)V", cancellable = true)
    private void injectPlayDelayed(SoundInstance sound, int delay, CallbackInfo ci) {
        if (sound == null) {
            return;
        }
        if (sound.getIdentifier().equals(MUSIC_MENU.key().identifier())) {
            ci.cancel();
        }
    }
}
