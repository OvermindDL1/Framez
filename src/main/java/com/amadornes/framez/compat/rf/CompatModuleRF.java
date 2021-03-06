package com.amadornes.framez.compat.rf;

import com.amadornes.framez.compat.CompatModule;
import com.amadornes.framez.modifier.MotorModifierRegistry;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class CompatModuleRF extends CompatModule {

    @Override
    public void preInit(FMLPreInitializationEvent ev) {

        MotorModifierRegistry.instance().registerModifier(new MotorModifierRF());
    }

}
