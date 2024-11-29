package com.Fishmod.mod_LavaCow.world.structure;

import com.Fishmod.mod_LavaCow.mod_LavaCow;
import com.Fishmod.mod_LavaCow.config.FURConfig;
import com.mojang.serialization.Codec;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class DesertTombStructure extends Structure<NoFeatureConfig> {
	
    public DesertTombStructure() {
        super(NoFeatureConfig.CODEC);
    }
    
	public DesertTombStructure(Codec<NoFeatureConfig> p_i231997_1_) {
		super(p_i231997_1_);
		this.setRegistryName(new ResourceLocation(mod_LavaCow.MODID, "desert_tomb"));
	}
	
	@Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.SURFACE_STRUCTURES;
    }
	
	@Override
	public IStartFactory<NoFeatureConfig> getStartFactory() {
		return DesertTombStructure.Start::new;
	}

    public static class Start extends StructureStart<NoFeatureConfig> {
        public Start(Structure<NoFeatureConfig> structure, int x, int z, MutableBoundingBox boundingBox, int refCount, long seed) {
            super(structure, x, z, boundingBox, refCount, seed);
        }

		@Override
		public void generatePieces(DynamicRegistries dynamicRegistries, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, 
				int x, int z, Biome biomeIn, NoFeatureConfig featureConfig) {			
			if(FURConfig.Generate_Desert_Tomb.get()) {
	            int k = (x << 4) + 7;
	            int l = (z << 4) + 7;
	            int i1 = chunkGenerator.getBaseHeight(k - 7, l - 7, Heightmap.Type.WORLD_SURFACE_WG);
	            int j1 = chunkGenerator.getBaseHeight(k + 7, l - 7, Heightmap.Type.WORLD_SURFACE_WG);
	            int k1 = chunkGenerator.getBaseHeight(k - 7, l + 7, Heightmap.Type.WORLD_SURFACE_WG);
	            int l1 = chunkGenerator.getBaseHeight(k + 7, l + 7, Heightmap.Type.WORLD_SURFACE_WG);
	            int i2 = Math.min(Math.min(i1, j1), Math.min(k1, l1));
				BlockPos blockpos = new BlockPos(k, i2 - 9, l);			
		        
				JigsawManager.addPieces(dynamicRegistries,
	                    new VillageConfig(() -> dynamicRegistries.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY)
	                    		.get(new ResourceLocation(mod_LavaCow.MODID, "desert_tomb/start_pool")),
	                            10), AbstractVillagePiece::new, chunkGenerator, templateManagerIn,
	                    blockpos, this.pieces, this.random, false, false);
	            
				DesertTombPiece desert_tomb_piece = new DesertTombPiece(this.random, (x << 4) + 7, (z << 4) + 7);
				this.pieces.add(desert_tomb_piece);
	            this.calculateBoundingBox();
	            
	            /*System.out.println("House at " +
	                    this.pieces.get(0).getBoundingBox().x0 + " " +
	                    this.pieces.get(0).getBoundingBox().y0 + " " +
	                    this.pieces.get(0).getBoundingBox().z0);*/
			}
		}
    }
}
