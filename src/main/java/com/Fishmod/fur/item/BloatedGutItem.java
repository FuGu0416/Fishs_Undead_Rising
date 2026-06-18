package com.Fishmod.fur.item;

import java.util.List;
import com.Fishmod.fur.mod_LavaCow;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

public class BloatedGutItem extends FURItem {

	public BloatedGutItem() {
		super(new Item.Properties(), 0, UseAnim.NONE, 1);
    }

    /**
     * Called when the equipped item is right clicked.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.SLIME_SQUISH, SoundSource.PLAYERS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            LootTable table = serverLevel.getServer().getLootData().getLootTable(new ResourceLocation(mod_LavaCow.MODID, "gameplay/bloated_gut"));

            LootParams params = new LootParams.Builder(serverLevel)
                    .withParameter(LootContextParams.ORIGIN, player.position())
                    .withParameter(LootContextParams.THIS_ENTITY, player)
                    .create(LootContextParamSets.GIFT);

            List<ItemStack> drops = table.getRandomItems(params);

            for (ItemStack drop : drops) {
    			player.drop(drop, true);
            }

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }
        }

    	return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
