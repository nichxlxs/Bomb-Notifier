package me.nicholxs.bombnotifier;

import net.minecraft.command.ICommand;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(
        modid = BombNotifier.MOD_ID,
        version = BombNotifier.VERSION,
        name = BombNotifier.MOD_NAME
)
public class BombNotifier {

    public static final String MOD_ID = "bombnotifier";
    public static final String MOD_NAME = "Bomb Notifier";
    public static final String VERSION = "1.1";

    private BombBellModule bombBellModule;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        bombBellModule = new BombBellModule();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(bombBellModule);

    }

}
