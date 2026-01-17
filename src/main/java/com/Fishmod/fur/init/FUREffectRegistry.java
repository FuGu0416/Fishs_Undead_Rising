package com.Fishmod.fur.init;

import com.Fishmod.fur.mod_LavaCow;
import com.Fishmod.fur.core.FURBrewingRecipe;
import com.Fishmod.fur.effect.EffectCharmingPheromone;
import com.Fishmod.fur.effect.EffectCorroded;
import com.Fishmod.fur.effect.EffectFear;
import com.Fishmod.fur.effect.EffectFlourished;
import com.Fishmod.fur.effect.EffectFragile;
import com.Fishmod.fur.effect.EffectImmolation;
import com.Fishmod.fur.effect.EffectInfested;
import com.Fishmod.fur.effect.EffectSoiled;
import com.Fishmod.fur.effect.EffectThorned;
import com.Fishmod.fur.effect.EffectVoidDust;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class FUREffectRegistry {
    public static final DeferredRegister<MobEffect> EFFECT_DEF_REG = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, mod_LavaCow.MODID);
    public static final DeferredRegister<Potion> POTION_DEF_REG = DeferredRegister.create(ForgeRegistries.POTIONS, mod_LavaCow.MODID);
    
	public static final RegistryObject<MobEffect> CORRODED = EFFECT_DEF_REG.register("corroded", ()-> new EffectCorroded());
	public static final RegistryObject<MobEffect> SOILED = EFFECT_DEF_REG.register("soiled", ()-> new EffectSoiled());
	public static final RegistryObject<MobEffect> INFESTED = EFFECT_DEF_REG.register("infested", ()-> new EffectInfested().addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<MobEffect> FRAGILE = EFFECT_DEF_REG.register("fragile", ()-> new EffectFragile());
	public static final RegistryObject<MobEffect> FEAR = EFFECT_DEF_REG.register("fear", ()-> new EffectFear().addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", -4.0D, AttributeModifier.Operation.ADDITION).addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)-0.05F, AttributeModifier.Operation.MULTIPLY_TOTAL));
	public static final RegistryObject<MobEffect> THORNED = EFFECT_DEF_REG.register("thorned", ()-> new EffectThorned());
	public static final RegistryObject<MobEffect> IMMOLATION = EFFECT_DEF_REG.register("immolation", ()-> new EffectImmolation());
	public static final RegistryObject<MobEffect> CHARMING_PHEROMONE = EFFECT_DEF_REG.register("charming_pheromone", ()-> new EffectCharmingPheromone());
	public static final RegistryObject<MobEffect> FLOURISHED = EFFECT_DEF_REG.register("flourished", ()-> new EffectFlourished());
	public static final RegistryObject<MobEffect> VOID_DUST = EFFECT_DEF_REG.register("void_dust", ()-> new EffectVoidDust());
	
	public static final RegistryObject<Potion> CORROSIVE_POTION = POTION_DEF_REG.register("corrosive", ()-> new Potion(new MobEffectInstance(CORRODED.get(), 900)));
	public static final RegistryObject<Potion> STRONG_CORROSIVE_POTION = POTION_DEF_REG.register("strong_corrosive", ()-> new Potion(new MobEffectInstance(CORRODED.get(), 900, 1)));
	public static final RegistryObject<Potion> LONG_CORROSIVE_POTION = POTION_DEF_REG.register("long_corrosive", ()-> new Potion(new MobEffectInstance(CORRODED.get(), 1800)));
	public static final RegistryObject<Potion> FOULODOR_POTION = POTION_DEF_REG.register("foulodor", ()-> new Potion(new MobEffectInstance(SOILED.get(), 900)));
	public static final RegistryObject<Potion> STRONG_FOULODOR_POTION = POTION_DEF_REG.register("strong_foulodor", ()-> new Potion(new MobEffectInstance(SOILED.get(), 900, 1)));
	public static final RegistryObject<Potion> LONG_FOULODOR_POTION = POTION_DEF_REG.register("long_foulodor", ()-> new Potion(new MobEffectInstance(SOILED.get(), 1800)));
	public static final RegistryObject<Potion> INFESTATION_POTION = POTION_DEF_REG.register("infestation", ()-> new Potion(new MobEffectInstance(INFESTED.get(), 1800)));
	public static final RegistryObject<Potion> STRONG_INFESTATION_POTION = POTION_DEF_REG.register("strong_infestation", ()-> new Potion(new MobEffectInstance(INFESTED.get(), 1800, 1)));
	public static final RegistryObject<Potion> LONG_INFESTATION_POTION = POTION_DEF_REG.register("long_infestation", ()-> new Potion(new MobEffectInstance(INFESTED.get(), 3600)));
	public static final RegistryObject<Potion> FRAGILE_POTION = POTION_DEF_REG.register("fragile", ()-> new Potion(new MobEffectInstance(FRAGILE.get(), 900)));
	public static final RegistryObject<Potion> STRONG_FRAGILE_POTION = POTION_DEF_REG.register("strong_fragile", ()-> new Potion(new MobEffectInstance(FRAGILE.get(), 900, 1)));
	public static final RegistryObject<Potion> LONG_FRAGILE_POTION = POTION_DEF_REG.register("long_fragile", ()-> new Potion(new MobEffectInstance(FRAGILE.get(), 1800)));
	public static final RegistryObject<Potion> THORN_POTION = POTION_DEF_REG.register("thorn", ()-> new Potion(new MobEffectInstance(THORNED.get(), 3600)));
	public static final RegistryObject<Potion> STRONG_THORN_POTION = POTION_DEF_REG.register("strong_thorn", ()-> new Potion(new MobEffectInstance(THORNED.get(), 1800, 1)));
	public static final RegistryObject<Potion> LONG_THORN_POTION = POTION_DEF_REG.register("long_thorn", ()-> new Potion(new MobEffectInstance(THORNED.get(), 9600)));
	public static final RegistryObject<Potion> IMMOLATION_POTION = POTION_DEF_REG.register("immolation", ()-> new Potion(new MobEffectInstance(IMMOLATION.get(), 3600)));
	public static final RegistryObject<Potion> STRONG_IMMOLATION_POTION = POTION_DEF_REG.register("strong_immolation", ()-> new Potion(new MobEffectInstance(IMMOLATION.get(), 1800, 1)));
	public static final RegistryObject<Potion> LONG_IMMOLATION_POTION = POTION_DEF_REG.register("long_immolation", ()-> new Potion(new MobEffectInstance(IMMOLATION.get(), 9600)));
	public static final RegistryObject<Potion> VOID_DUST_POTION = POTION_DEF_REG.register("void_dust", ()-> new Potion(new MobEffectInstance(VOID_DUST.get(), 900)));
	public static final RegistryObject<Potion> STRONG_VOID_DUST_POTION = POTION_DEF_REG.register("strong_void_dust", ()-> new Potion(new MobEffectInstance(VOID_DUST.get(), 450, 2)));
	public static final RegistryObject<Potion> LONG_VOID_DUST_POTION = POTION_DEF_REG.register("long_void_dust", ()-> new Potion(new MobEffectInstance(VOID_DUST.get(), 1800)));
	
    public static ItemStack createPotion(RegistryObject<Potion> potion){
        return  PotionUtils.setPotion(new ItemStack(Items.POTION), potion.get());
    }

    public static ItemStack createPotion(Potion potion){
        return  PotionUtils.setPotion(new ItemStack(Items.POTION), potion);
    }
    
    public static ItemStack createPotion(Item potionType, Potion potion){
        return  PotionUtils.setPotion(new ItemStack(potionType), potion);
    }
    
    public static void onInitItems() {
    	/* Brew into special potions */
        //BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.STRONG_REGENERATION)), Ingredient.of(new ItemStack(FURItemRegistry.HYPHAE)), new ItemStack(FURItemRegistry.FISSIONPOTION)));
        //BrewingRecipeRegistry.addRecipe(Ingredient.of(new ItemStack(FURItemRegistry.FISSIONPOTION)), Ingredient.of(new ItemStack(FURItemRegistry.MOOTENHEART)), new ItemStack(FURItemRegistry.POTION_OF_MOOTEN_LAVA));
        //BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.PHEROMONE_GLAND)), new ItemStack(FURItemRegistry.CHARMING_CATALYST)));
        //BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.HOLY_SLUDGE)), new ItemStack(FURItemRegistry.HOLY_WATER)));
        
        /* Brew into typical potions */
        //BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.PTERA_WING)), createPotion(Items.POTION, Potions.SLOW_FALLING)));
        
        /*BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ACIDICHEART)), createPotion(Items.POTION, CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ACIDICHEART)), createPotion(Items.LINGERING_POTION, CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ACIDICHEART)), createPotion(Items.SPLASH_POTION, CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, CORROSIVE_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, CORROSIVE_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, CORROSIVE_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, CORROSIVE_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, CORROSIVE_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_CORROSIVE_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, CORROSIVE_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_CORROSIVE_POTION)));*/
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.FOUL_BRISTLE.get())), createPotion(Items.POTION, FOULODOR_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.FOUL_BRISTLE.get())), createPotion(Items.LINGERING_POTION, FOULODOR_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.FOUL_BRISTLE.get())), createPotion(Items.SPLASH_POTION, FOULODOR_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, FOULODOR_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_FOULODOR_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, FOULODOR_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_FOULODOR_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, FOULODOR_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_FOULODOR_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, FOULODOR_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_FOULODOR_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, FOULODOR_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_FOULODOR_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, FOULODOR_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_FOULODOR_POTION.get())));
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.POISON_SPORE.get())), createPotion(Items.POTION, Potions.LONG_POISON)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.POISON_SPORE.get())), createPotion(Items.LINGERING_POTION, Potions.LONG_POISON)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.POISON_SPORE.get())), createPotion(Items.SPLASH_POTION, Potions.LONG_POISON)));
        
        /*BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.PARASITE_OVUM)), createPotion(Items.POTION, INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.PARASITE_OVUM)), createPotion(Items.LINGERING_POTION, INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.PARASITE_OVUM)), createPotion(Items.SPLASH_POTION, INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, INFESTATION_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, INFESTATION_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, INFESTATION_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, INFESTATION_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, INFESTATION_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_INFESTATION_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, INFESTATION_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_INFESTATION_POTION)));*/
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.HATRED_SHARD.get())), createPotion(Items.POTION, FRAGILE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.HATRED_SHARD.get())), createPotion(Items.LINGERING_POTION, FRAGILE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.HATRED_SHARD.get())), createPotion(Items.SPLASH_POTION, FRAGILE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, FRAGILE_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_FRAGILE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, FRAGILE_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_FRAGILE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, FRAGILE_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_FRAGILE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, FRAGILE_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_FRAGILE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, FRAGILE_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_FRAGILE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, FRAGILE_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_FRAGILE_POTION.get())));
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.CACTUS_FRUIT.get())), createPotion(Items.POTION, THORN_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.CACTUS_FRUIT.get())), createPotion(Items.LINGERING_POTION, THORN_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.CACTUS_FRUIT.get())), createPotion(Items.SPLASH_POTION, THORN_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, THORN_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_THORN_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, THORN_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_THORN_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, THORN_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_THORN_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, THORN_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_THORN_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, THORN_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_THORN_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, THORN_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_THORN_POTION.get())));
    
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.FIRE_RESISTANCE)), Ingredient.of(new ItemStack(FURItemRegistry.IMP_HORN.get())), createPotion(Items.POTION, IMMOLATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.FIRE_RESISTANCE)), Ingredient.of(new ItemStack(FURItemRegistry.IMP_HORN.get())), createPotion(Items.LINGERING_POTION, IMMOLATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.FIRE_RESISTANCE)), Ingredient.of(new ItemStack(FURItemRegistry.IMP_HORN.get())), createPotion(Items.SPLASH_POTION, IMMOLATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, IMMOLATION_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_IMMOLATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, IMMOLATION_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_IMMOLATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, IMMOLATION_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_IMMOLATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, IMMOLATION_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_IMMOLATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, IMMOLATION_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_IMMOLATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, IMMOLATION_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_IMMOLATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.LONG_FIRE_RESISTANCE)), Ingredient.of(new ItemStack(FURItemRegistry.IMP_HORN.get())), createPotion(Items.POTION, LONG_IMMOLATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.LONG_FIRE_RESISTANCE)), Ingredient.of(new ItemStack(FURItemRegistry.IMP_HORN.get())), createPotion(Items.LINGERING_POTION, LONG_IMMOLATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.LONG_FIRE_RESISTANCE)), Ingredient.of(new ItemStack(FURItemRegistry.IMP_HORN.get())), createPotion(Items.SPLASH_POTION, LONG_IMMOLATION_POTION.get())));
        
        /*BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ENIGMOTH_LARVA)), createPotion(Items.POTION, VOID_DUST_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ENIGMOTH_LARVA)), createPotion(Items.LINGERING_POTION, VOID_DUST_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ENIGMOTH_LARVA)), createPotion(Items.SPLASH_POTION, VOID_DUST_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, VOID_DUST_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_VOID_DUST_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, VOID_DUST_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_VOID_DUST_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, VOID_DUST_POTION)), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_VOID_DUST_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, VOID_DUST_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_VOID_DUST_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, VOID_DUST_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_VOID_DUST_POTION)));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, VOID_DUST_POTION)), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_VOID_DUST_POTION)));*/
    }
}
