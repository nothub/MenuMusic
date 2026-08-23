package hub.lol.menumusic.neoforge;

import hub.lol.menumusic.MenuMusicCore;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(value = "menumusic", dist = {Dist.CLIENT})
public class Main {

    public Main(IEventBus modEventBus, ModContainer modContainer) {
        var core = new MenuMusicCore();
        NeoForge.EVENT_BUS.addListener((ClientTickEvent.Post event) -> core.onTick(Minecraft.getInstance()));
    }

}
