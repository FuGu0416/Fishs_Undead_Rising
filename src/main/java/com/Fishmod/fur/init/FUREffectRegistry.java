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
import com.Fishmod.fur.effect.EffectPossessed;
import com.Fishmod.fur.effect.EffectSoiled;
import com.Fishmod.fur.effect.EffectSoulSiphon;
import com.Fishmod.fur.effect.EffectSporerot;
import com.Fishmod.fur.effect.EffectThorned;
import com.Fishmod.fur.effect.EffectVenomous;
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
	public static final RegistryObject<MobEffect> VENOMOUS = EFFECT_DEF_REG.register("venomous", ()-> new EffectVenomous());
	public static final RegistryObject<MobEffect> POSSESSED = EFFECT_DEF_REG.register("possessed", ()-> new EffectPossessed().addAttributeModifier(Attributes.MOVEMENT_SPEED, "91AEAA56-376B-4498-935B-2F7F68070635", (double)0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL).addAttributeModifier(Attributes.ATTACK_DAMAGE, "648D7064-6A60-4F59-8ABE-C2C23A6DD7A9", 0.0D, AttributeModifier.Operation.ADDITION));
	public static final RegistryObject<MobEffect> SOUL_SIPHON = EFFECT_DEF_REG.register("soul_siphon", ()-> new EffectSoulSiphon());
	public static final RegistryObject<MobEffect> SPOREROT = EFFECT_DEF_REG.register("sporerot", ()-> new EffectSporerot().addAttributeModifier(Attributes.MOVEMENT_SPEED, "0CAA59A6-6C88-4488-9518-CAC342D2D71B", (double)-0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL));

	/**
	 * Builds a FEAR instance with the vanilla potion swirl hidden (ambient off, particles off, icon on).
	 * The in-world effect is drawn with a custom particle in {@code FURServerEvents#onELiving}, so the
	 * swirl is suppressed here — always create FEAR through this so no application site re-shows it.
	 */
	public static MobEffectInstance fear(int duration, int amplifier) {
		return new MobEffectInstance(FEAR.get(), duration, amplifier, false, false, true);
	}

	/** As {@link #fear} but for IMMOLATION — swirl hidden, custom flame particle drawn instead. */
	public static MobEffectInstance immolation(int duration, int amplifier) {
		return new MobEffectInstance(IMMOLATION.get(), duration, amplifier, false, false, true);
	}

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
	public static final RegistryObject<Potion> IMMOLATION_POTION = POTION_DEF_REG.register("immolation", ()-> new Potion(immolation(3600, 0)));
	public static final RegistryObject<Potion> STRONG_IMMOLATION_POTION = POTION_DEF_REG.register("strong_immolation", ()-> new Potion(immolation(1800, 1)));
	public static final RegistryObject<Potion> LONG_IMMOLATION_POTION = POTION_DEF_REG.register("long_immolation", ()-> new Potion(immolation(9600, 0)));
	public static final RegistryObject<Potion> VOID_DUST_POTION = POTION_DEF_REG.register("void_dust", ()-> new Potion(new MobEffectInstance(VOID_DUST.get(), 900)));
	public static final RegistryObject<Potion> STRONG_VOID_DUST_POTION = POTION_DEF_REG.register("strong_void_dust", ()-> new Potion(new MobEffectInstance(VOID_DUST.get(), 450, 2)));
	public static final RegistryObject<Potion> LONG_VOID_DUST_POTION = POTION_DEF_REG.register("long_void_dust", ()-> new Potion(new MobEffectInstance(VOID_DUST.get(), 1800)));
	public static final RegistryObject<Potion> VENOMOUS_POTION = POTION_DEF_REG.register("venomous", ()-> new Potion(new MobEffectInstance(VENOMOUS.get(), 3600)));
	public static final RegistryObject<Potion> STRONG_VENOMOUS_POTION = POTION_DEF_REG.register("strong_venomous", ()-> new Potion(new MobEffectInstance(VENOMOUS.get(), 1800, 1)));
	public static final RegistryObject<Potion> LONG_VENOMOUS_POTION = POTION_DEF_REG.register("long_venomous", ()-> new Potion(new MobEffectInstance(VENOMOUS.get(), 9600)));
	public static final RegistryObject<Potion> SOUL_SIPHON_POTION = POTION_DEF_REG.register("soul_siphon", ()-> new Potion(new MobEffectInstance(SOUL_SIPHON.get(), 400)));
	public static final RegistryObject<Potion> STRONG_SOUL_SIPHON_POTION = POTION_DEF_REG.register("strong_soul_siphon", ()-> new Potion(new MobEffectInstance(SOUL_SIPHON.get(), 200, 1)));
	public static final RegistryObject<Potion> LONG_SOUL_SIPHON_POTION = POTION_DEF_REG.register("long_soul_siphon", ()-> new Potion(new MobEffectInstance(SOUL_SIPHON.get(), 800)));
	public static final RegistryObject<Potion> SPOREROT_POTION = POTION_DEF_REG.register("sporerot", ()-> new Potion(new MobEffectInstance(SPOREROT.get(), 900)));
	public static final RegistryObject<Potion> STRONG_SPOREROT_POTION = POTION_DEF_REG.register("strong_sporerot", ()-> new Potion(new MobEffectInstance(SPOREROT.get(), 900, 1)));
	public static final RegistryObject<Potion> LONG_SPOREROT_POTION = POTION_DEF_REG.register("long_sporerot", ()-> new Potion(new MobEffectInstance(SPOREROT.get(), 1800)));

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
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.FISSION_REAGENT.get())), new ItemStack(FURItemRegistry.POTION_OF_FISSION.get())));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(new ItemStack(FURItemRegistry.POTION_OF_FISSION.get())), Ingredient.of(new ItemStack(FURItemRegistry.MOOTEN_REAGENT.get())), new ItemStack(FURItemRegistry.POTION_OF_MOOTEN_LAVA.get()));
        //BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.PHEROMONE_GLAND)), new ItemStack(FURItemRegistry.CHARMING_CATALYST)));
        
        /* Brew into typical potions */
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.PTERA_WING_RAW.get())), createPotion(Items.POTION, Potions.SLOW_FALLING)));
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ACIDIC_HEART.get())), createPotion(Items.POTION, CORROSIVE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ACIDIC_HEART.get())), createPotion(Items.LINGERING_POTION, CORROSIVE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ACIDIC_HEART.get())), createPotion(Items.SPLASH_POTION, CORROSIVE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, CORROSIVE_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_CORROSIVE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, CORROSIVE_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_CORROSIVE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, CORROSIVE_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_CORROSIVE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, CORROSIVE_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_CORROSIVE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, CORROSIVE_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_CORROSIVE_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, CORROSIVE_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_CORROSIVE_POTION.get())));
        
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
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.VESPA_OVUM.get())), createPotion(Items.POTION, INFESTATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.VESPA_OVUM.get())), createPotion(Items.LINGERING_POTION, INFESTATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.SLOWNESS)), Ingredient.of(new ItemStack(FURItemRegistry.VESPA_OVUM.get())), createPotion(Items.SPLASH_POTION, INFESTATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, INFESTATION_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_INFESTATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, INFESTATION_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_INFESTATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, INFESTATION_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_INFESTATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, INFESTATION_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_INFESTATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, INFESTATION_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_INFESTATION_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, INFESTATION_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_INFESTATION_POTION.get())));
        
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
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ENIGMOTH_DUST.get())), createPotion(Items.POTION, VOID_DUST_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ENIGMOTH_DUST.get())), createPotion(Items.LINGERING_POTION, VOID_DUST_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.ENIGMOTH_DUST.get())), createPotion(Items.SPLASH_POTION, VOID_DUST_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, VOID_DUST_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_VOID_DUST_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, VOID_DUST_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_VOID_DUST_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, VOID_DUST_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_VOID_DUST_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, VOID_DUST_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_VOID_DUST_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, VOID_DUST_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_VOID_DUST_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, VOID_DUST_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_VOID_DUST_POTION.get())));
        
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.POISON_STINGER.get())), createPotion(Items.POTION, VENOMOUS_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.POISON_STINGER.get())), createPotion(Items.LINGERING_POTION, VENOMOUS_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.POISON_STINGER.get())), createPotion(Items.SPLASH_POTION, VENOMOUS_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, VENOMOUS_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_VENOMOUS_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, VENOMOUS_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_VENOMOUS_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, VENOMOUS_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_VENOMOUS_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, VENOMOUS_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_VENOMOUS_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, VENOMOUS_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_VENOMOUS_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, VENOMOUS_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_VENOMOUS_POTION.get())));

        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.UNDYING_HEART.get())), createPotion(Items.POTION, SOUL_SIPHON_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.UNDYING_HEART.get())), createPotion(Items.LINGERING_POTION, SOUL_SIPHON_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURItemRegistry.UNDYING_HEART.get())), createPotion(Items.SPLASH_POTION, SOUL_SIPHON_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, SOUL_SIPHON_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_SOUL_SIPHON_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, SOUL_SIPHON_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_SOUL_SIPHON_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, SOUL_SIPHON_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_SOUL_SIPHON_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, SOUL_SIPHON_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_SOUL_SIPHON_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, SOUL_SIPHON_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_SOUL_SIPHON_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, SOUL_SIPHON_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_SOUL_SIPHON_POTION.get())));

        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURBlockRegistry.GLOWSHROOM.get())), createPotion(Items.POTION, SPOREROT_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURBlockRegistry.GLOWSHROOM.get())), createPotion(Items.LINGERING_POTION, SPOREROT_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, Potions.AWKWARD)), Ingredient.of(new ItemStack(FURBlockRegistry.GLOWSHROOM.get())), createPotion(Items.SPLASH_POTION, SPOREROT_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, SPOREROT_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.POTION, LONG_SPOREROT_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, SPOREROT_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.LINGERING_POTION, LONG_SPOREROT_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, SPOREROT_POTION.get())), Ingredient.of(new ItemStack(Items.REDSTONE)), createPotion(Items.SPLASH_POTION, LONG_SPOREROT_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.POTION, SPOREROT_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.POTION, STRONG_SPOREROT_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.LINGERING_POTION, SPOREROT_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.LINGERING_POTION, STRONG_SPOREROT_POTION.get())));
        BrewingRecipeRegistry.addRecipe(new FURBrewingRecipe(Ingredient.of(createPotion(Items.SPLASH_POTION, SPOREROT_POTION.get())), Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)), createPotion(Items.SPLASH_POTION, STRONG_SPOREROT_POTION.get())));
    }
}
