package com.amadornes.framez.client.model;

import java.util.Collections;
import java.util.List;

import com.amadornes.framez.block.BlockMotor;
import com.amadornes.framez.motor.logic.MotorLogicType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IExtendedBlockState;

@SuppressWarnings("deprecation")
public class ModelMotorCamouflageHandler implements IBakedModel {

    private IBakedModel model;

    public ModelMotorCamouflageHandler(IBakedModel model) {

        this.model = model;
    }

    @Override
    public List<BakedQuad> getQuads(IBlockState state, EnumFacing side, long rand) {

        if (!(state instanceof IExtendedBlockState)
                || !((IExtendedBlockState) state).getUnlistedNames().contains(BlockMotor.PROPERTY_CAMO_DOWN)) {
            return Collections.emptyList();
        }
        IExtendedBlockState s = (IExtendedBlockState) state;
        IBlockState[] faces = new IBlockState[] { s.getValue(BlockMotor.PROPERTY_CAMO_DOWN), s.getValue(BlockMotor.PROPERTY_CAMO_UP),
                s.getValue(BlockMotor.PROPERTY_CAMO_NORTH), s.getValue(BlockMotor.PROPERTY_CAMO_SOUTH),
                s.getValue(BlockMotor.PROPERTY_CAMO_WEST), s.getValue(BlockMotor.PROPERTY_CAMO_EAST) };

        if (side != null && faces[side.ordinal()] != null) {
            IBakedModel model = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes()
                    .getModelForState(faces[side.ordinal()]);
            if (model != null) {
                boolean overrideFace = !MotorLogicType.VALUES[state.getValue(BlockMotor.PROPERTY_LOGIC_TYPE)].hasHead;
                // TODO: Check motor setting
                if (overrideFace) {
                    return model.getQuads(faces[side.ordinal()], side, rand);
                } else {
                    List<BakedQuad> quads = model.getQuads(faces[side.ordinal()], side, rand);
                    if (!quads.isEmpty()) {//
                        return AdvancedModelRextexturer.retexture(state, rand, this.model, quads.get(0).getSprite()).getQuads(state, side,
                                rand);
                    }
                }
            }
        }
        return model.getQuads(state, side, rand);
    }

    @Override
    public boolean isAmbientOcclusion() {

        return model.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {

        return model.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {

        return model.isBuiltInRenderer();
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {

        return model.getParticleTexture();
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {

        return model.getItemCameraTransforms();
    }

    @Override
    public ItemOverrideList getOverrides() {

        return model.getOverrides();
    }

}
