package com.Fishmod.mod_LavaCow.structure;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureUtil {
	
    public static boolean CanStructureGenonBlock(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        
        if (!state.isOpaqueCube() || !state.isSideSolid(world, pos, EnumFacing.UP)) {
            return false;
        }
        
        return !world.getBlockState(pos.up()).isOpaqueCube() && !(world.getBlockState(pos.up(2)).getBlock() instanceof BlockLiquid);
    }
    
    public static void GenStructureWithLoot(ResourceLocation structure, ResourceLocation loot, World world, BlockPos pos) {
        EnumFacing facing = EnumFacing.getHorizontal(world.rand.nextInt(4));
        Rotation rotation = getRotationFromFacing(facing);
        MinecraftServer server = world.getMinecraftServer();
        TemplateManager templateManager = world.getSaveHandler().getStructureTemplateManager();
        PlacementSettings settings = new PlacementSettings().setRotation(rotation).setMirror(Mirror.NONE);
        Template template = templateManager.getTemplate(server, structure);
        
        template.addBlocksToWorld(world, pos, new BlockProcessorLoot(pos, settings, loot), settings, 2);
    }
    
    public static Rotation getRotationFromFacing(EnumFacing facing) {
        switch (facing) {
            case EAST:
                return Rotation.CLOCKWISE_90;
            case SOUTH:
                return Rotation.CLOCKWISE_180;
            case WEST:
                return Rotation.COUNTERCLOCKWISE_90;
            default:
                return Rotation.NONE;
        }
    }
}
