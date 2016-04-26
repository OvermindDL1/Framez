package com.amadornes.framez.api.frame;

import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public interface IFrameRegistry {

    public void registerMaterial(IFrameMaterial material);

    public void registerStickinessProvider(IStickinessProvider provider);

    public void registerStickinessProvider(IStickinessProvider provider, int priority);

    public ISticky getSticky(IBlockAccess world, BlockPos pos, EnumFacing face);

    public IStickable getStickable(IBlockAccess world, BlockPos pos, EnumFacing face);

    public IFrame getFrame(IBlockAccess world, BlockPos pos);

}