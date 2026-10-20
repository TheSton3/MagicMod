package com.thestone.mm.entity;

import com.lowdragmc.photon.client.fx.EntityEffectExecutor;
import com.lowdragmc.photon.client.fx.FX;
import com.lowdragmc.photon.client.fx.FXHelper;
import com.thestone.mm.core.ModEntities;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

public class MagicMissileEntity extends AbstractArrow {

    FX fx = FXHelper.getFX(ResourceLocation.parse("photon:magic_missile_trail"));

    public MagicMissileEntity(EntityType<? extends MagicMissileEntity> entityType, Level level) {
        super(entityType, level);
    }

    public MagicMissileEntity(Level level, LivingEntity shooter, ItemStack pickupItemStack) {
        super(ModEntities.MAGIC_MISSILE.get(), shooter, level, pickupItemStack, null);

    }

    public MagicMissileEntity(Level level, double x, double y, double z, ItemStack pickupItemStack) {
        super(ModEntities.MAGIC_MISSILE.get(), x, y, z, level, pickupItemStack, pickupItemStack);

    }



    @Override
    public void tick() {
        super.tick();
        new EntityEffectExecutor(fx, this.level(), this, EntityEffectExecutor.AutoRotate.NONE).start();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        this.discard();
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return ItemStack.EMPTY;
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        this.discard();
    }

}
