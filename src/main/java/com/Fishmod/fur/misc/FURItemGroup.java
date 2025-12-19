package com.Fishmod.fur.misc;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.init.FURItemRegistry;
import com.Fishmod.fur.init.FURBlockRegistry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class FURItemGroup {
	public static final DeferredRegister<CreativeModeTab> DEF_REG = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, mod_LavaCow.MODID);

	public static final RegistryObject<CreativeModeTab> TAB = DEF_REG.register("tab", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.fur.tab"))
            .icon(() -> new ItemStack(FURItemRegistry.UNDYINGHEART.get()))
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .displayItems((params, output) -> {
            	output.accept(FURItemRegistry.SHARP_FANG.get());
            	output.accept(FURItemRegistry.BONE_SWORD.get());
            	output.accept(FURItemRegistry.CHITIN.get());
            	output.accept(FURItemRegistry.CHITIN_ARMOR_HELMET.get());
            	output.accept(FURItemRegistry.CHITIN_ARMOR_CHESTPLATE.get());
            	output.accept(FURItemRegistry.CHITIN_ARMOR_LEGGINGS.get());
            	output.accept(FURItemRegistry.CHITIN_ARMOR_BOOTS.get());
            	output.accept(FURItemRegistry.BASIC_BOMB.get());
            	output.accept(FURItemRegistry.GHOSTBOMB.get());
            	output.accept(FURItemRegistry.HOLY_GRENADE.get());
            	output.accept(FURItemRegistry.SONICBOMB.get());
            	output.accept(FURItemRegistry.WETA_JAW.get());
            	output.accept(FURItemRegistry.WETA_HOE.get());
            	output.accept(FURItemRegistry.FOUL_BRISTLE.get());
            	output.accept(FURItemRegistry.FOUL_HIDE.get());
            	output.accept(FURItemRegistry.FAMINE_ARMOR_HELMET.get());
            	output.accept(FURItemRegistry.FAMINE_ARMOR_CHESTPLATE.get());
            	output.accept(FURItemRegistry.FAMINE_ARMOR_LEGGINGS.get());
            	output.accept(FURItemRegistry.FAMINE_ARMOR_BOOTS.get());
            	output.accept(FURItemRegistry.SCYTHE_CLAW.get());
            	output.accept(FURItemRegistry.REAPERS_SCYTHE.get());
            	output.accept(FURItemRegistry.FAMINE.get());
            	output.accept(FURItemRegistry.IMP_HORN.get());
            	output.accept(FURItemRegistry.KUNG_PAO_CHICKEN.get());
            	output.accept(FURItemRegistry.CURSED_FABRIC.get());
            	output.accept(FURItemRegistry.CURSEWEAVE_CLOTH.get());
            	output.accept(FURItemRegistry.ECTOPLASM.get());
            	output.accept(FURItemRegistry.ECTOPLASM_MASS.get());
            	output.accept(FURItemRegistry.ECTOPLASM_INGOT.get());
            	output.accept(FURBlockRegistry.ECTOPLASM_BLOCK.get());
            	output.accept(FURItemRegistry.GHOSTLY_ARMOR_HELMET.get());
            	output.accept(FURItemRegistry.GHOSTLY_ARMOR_CHESTPLATE.get());
            	output.accept(FURItemRegistry.GHOSTLY_ARMOR_LEGGINGS.get());
            	output.accept(FURItemRegistry.GHOSTLY_ARMOR_BOOTS.get());
            	output.accept(FURItemRegistry.WISP_ASHES.get());
            	output.accept(FURItemRegistry.WISP_IN_A_BOTTLE.get());
            	output.accept(FURItemRegistry.MOLTEN_MEAT.get());
            	output.accept(FURItemRegistry.MOLTEN_ALLOY.get());            	
            	output.accept(FURItemRegistry.MOLTEN_AXE.get());
            	output.accept(FURItemRegistry.MOLTEN_HAMMER.get());
            	output.accept(FURItemRegistry.MOLTEN_ARMOR_HELMET.get());
            	output.accept(FURItemRegistry.MOLTEN_ARMOR_CHESTPLATE.get());
            	output.accept(FURItemRegistry.MOLTEN_ARMOR_LEGGINGS.get());
            	output.accept(FURItemRegistry.MOLTEN_ARMOR_BOOTS.get());
            	output.accept(FURItemRegistry.UNDYINGHEART.get());
            	output.accept(FURItemRegistry.MOOTENHEART.get());
            	output.accept(FURItemRegistry.SOULFIREHEART.get());
            	output.accept(FURItemRegistry.ACIDICHEART.get());
            	output.accept(FURItemRegistry.PIRANHA_BUCKET.get());
            	output.accept(FURItemRegistry.SWARMER_BUCKET.get());
            	output.accept(FURItemRegistry.CACTOID_POT.get());
            	output.accept(FURItemRegistry.PIRANHA_RAW.get());
            	output.accept(FURItemRegistry.PIRANHA_COOKED.get());           	
            	output.accept(FURItemRegistry.SWARMER_RAW.get());
            	output.accept(FURItemRegistry.SWARMER_COOKED.get());
            	output.accept(FURItemRegistry.CACTUS_THORN.get());
            	output.accept(FURItemRegistry.CACTUS_FRUIT.get());
            	output.accept(FURBlockRegistry.CACTOID_SPROUT.get());
            	output.accept(FURItemRegistry.BOABING.get());
            	output.accept(FURItemRegistry.HATRED_SHARD.get());
            	output.accept(FURBlockRegistry.SCARECROWHEAD_COMMON.get());
            	output.accept(FURBlockRegistry.SCARECROWHEAD_STRAW.get());
            	output.accept(FURBlockRegistry.SCARECROWHEAD_PLAGUE.get());
            	output.accept(FURItemRegistry.DISEASED_WHEAT.get());
            	output.accept(FURItemRegistry.DISEASED_BREAD.get());
            	output.accept(FURBlockRegistry.DISEASED_HAY_BLOCK.get());
            	output.accept(FURItemRegistry.FEATHER_BLACK.get());
            	output.accept(FURItemRegistry.HYPHAE.get());
            	output.accept(FURItemRegistry.POISONSPORE.get());
            	output.accept(FURItemRegistry.FROZENTHIGH.get());            	
            	output.accept(FURItemRegistry.MIMIC_CLAW.get());
            	output.accept(FURItemRegistry.MIMIC_CLAW_COOKED.get());
            	output.accept(FURItemRegistry.UNDERTAKER_SHOVEL.get());
            	FURItemRegistry.creativeTabSpawnEggMap.forEach((spawnEgg -> output.accept(spawnEgg.get())));
            })
            .build());
}
