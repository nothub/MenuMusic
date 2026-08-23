package hub.lol.menumusic.fabric;

import hub.lol.menumusic.MenuMusicCore;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class Main implements ModInitializer {

    @Override
    public void onInitialize() {
        var core = new MenuMusicCore();
        ClientTickEvents.END_CLIENT_TICK.register(core::onTick);
    }

}
