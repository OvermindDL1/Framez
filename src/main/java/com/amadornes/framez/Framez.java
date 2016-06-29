package com.amadornes.framez;

import com.amadornes.framez.api.motor.IMotorInteractions;
import com.amadornes.framez.frame.FrameMaterialBasic;
import com.amadornes.framez.frame.FrameRegistry;
import com.amadornes.framez.init.FramezBlocks;
import com.amadornes.framez.init.FramezCapabilities;
import com.amadornes.framez.init.FramezItems;
import com.amadornes.framez.init.FramezOredict;
import com.amadornes.framez.init.FramezParts;
import com.amadornes.framez.init.FramezRecipes;
import com.amadornes.framez.motor.MotorRegistry;
import com.amadornes.framez.motor.upgrade.UpgradeBase;
import com.amadornes.framez.motor.upgrade.UpgradeCamouflage;
import com.amadornes.framez.motor.upgrade.UpgradeCreative;
import com.amadornes.framez.motor.upgrade.UpgradeFactoryBase;
import com.amadornes.framez.motor.upgrade.UpgradeFactoryBase.IMotorUpgradeCreatorInt;
import com.amadornes.framez.network.GuiHandler;
import com.amadornes.framez.network.NetworkHandler;
import com.amadornes.framez.world.WorldGenerationHandler;
import com.amadornes.jtraits.JTrait;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = ModInfo.MODID, name = ModInfo.NAME, version = ModInfo.VERSION, dependencies = "required-after:mcmultipart")
public class Framez {

    @Instance
    public static Framez instance;
    @SidedProxy(serverSide = "com.amadornes.framez.CommonProxy", clientSide = "com.amadornes.framez.client.ClientProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(proxy);

        FrameRegistry.INSTANCE.registerMaterial(new FrameMaterialBasic("wood", 40, 40, 2, 6, "stickWood"));
        FrameRegistry.INSTANCE.registerMaterial(new FrameMaterialBasic("iron", 30, 20, 6, 3, "nuggetIron"));
        FrameRegistry.INSTANCE.registerMaterial(new FrameMaterialBasic("gold", 80, 10, 4, 2, "nuggetGold"));

        registerUpgrade("camouflage", (m, s) -> new UpgradeCamouflage(m, s));
        registerUpgrade("telekinetic", (m, s) -> new UpgradeBase(m, s));
        registerUpgrade("advTrigger", (m, s) -> new UpgradeBase(m, s));
        registerUpgrade("crawling", (m, s) -> new UpgradeBase(m, s));
        registerUpgrade("speedReg", (m, s) -> new UpgradeBase(m, s));
        registerUpgrade("computerized", (m, s) -> new UpgradeBase(m, s));
        registerUpgrade("stepInterp", (m, s) -> new UpgradeBase(m, s));
        registerUpgrade("soundMuffler", (m, s) -> new UpgradeBase(m, s));
        registerUpgrade("extRange", (m, s) -> new UpgradeBase(m, s));
        registerUpgrade("creative", (m, s) -> new UpgradeCreative(m, s));

        FramezItems.initialize();
        FramezItems.register();
        FramezBlocks.initialize();
        FramezBlocks.register();
        FramezParts.register();

        FramezCapabilities.register();
        FramezOredict.register();
        FramezRecipes.register();

        GameRegistry.registerWorldGenerator(new WorldGenerationHandler(), 0);

        proxy.preInit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {

        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        NetworkHandler.init();

        proxy.init();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        proxy.postInit();
    }

    private void registerUpgrade(String name, IMotorUpgradeCreatorInt creator) {

        registerUpgrade(name, creator, null);
    }

    private void registerUpgrade(String name, IMotorUpgradeCreatorInt creator,
            Class<? extends JTrait<? extends IMotorInteractions>> trait) {

        ResourceLocation type = new ResourceLocation(ModInfo.MODID, name);
        MotorRegistry.INSTANCE.registerUpgradeInternal(type,
                new UpgradeFactoryBase(type, (m, s) -> creator.createUpgrade(m, s).setType(type), trait));
    }

}
