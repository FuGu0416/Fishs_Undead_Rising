package com.Fishmod.mod_LavaCow.init;

import java.lang.reflect.Field;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.tileentity.ScarecrowHead_commonTileEntity;
import com.Fishmod.mod_LavaCow.tileentity.ScarecrowHead_plagueTileEntity;
import com.Fishmod.mod_LavaCow.tileentity.ScarecrowHead_strawTileEntity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = mod_LavaCow.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FURTileEntityRegistry {
	public static TileEntityType<ScarecrowHead_commonTileEntity> SCARECROWHEAD_COMMON = registerTileEntity(TileEntityType.Builder.of(ScarecrowHead_commonTileEntity::new, FURBlockRegistry.SCARECROWHEAD_COMMON), "scarecrowhead_common");
	public static TileEntityType<ScarecrowHead_strawTileEntity> SCARECROWHEAD_STRAW = registerTileEntity(TileEntityType.Builder.of(ScarecrowHead_strawTileEntity::new, FURBlockRegistry.SCARECROWHEAD_STRAW), "scarecrowhead_straw");
	public static TileEntityType<ScarecrowHead_plagueTileEntity> SCARECROWHEAD_PLAGUE = registerTileEntity(TileEntityType.Builder.of(ScarecrowHead_plagueTileEntity::new, FURBlockRegistry.SCARECROWHEAD_PLAGUE), "scarecrowhead_plague");
    
	@SuppressWarnings("unchecked")
	public static <T extends TileEntity> TileEntityType<T> registerTileEntity(TileEntityType.Builder<T> builder, String entityName){
        ResourceLocation nameLoc = new ResourceLocation(mod_LavaCow.MODID, entityName);
        return (TileEntityType<T>) builder.build(null).setRegistryName(nameLoc);
    }

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        try {
            for (Field f : FURTileEntityRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof TileEntityType) {
                    event.getRegistry().register((TileEntityType<?>) obj);
                } else if (obj instanceof TileEntityType[]) {
                    for (TileEntityType<?> te : (TileEntityType[]) obj) {
                        event.getRegistry().register(te);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
