package com.Fishmod.mod_LavaCow.tileentity;

import net.minecraft.block.BlockSkull;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityScarecrowHead extends TileEntity {
	private int skullVariant;
	private int skullRotation;
	
    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
        super.writeToNBT(compound);
        compound.setByte("SkullVariant", (byte)(this.skullVariant & 255));        
        compound.setByte("Rot", (byte)(this.skullRotation & 255));
        
        return compound;
    }

    public void readFromNBT(NBTTagCompound compound)
    {
        super.readFromNBT(compound);
        this.skullVariant = compound.getByte("SkullVariant");
        this.skullRotation = compound.getByte("Rot");
    }
    
    public int getSkullType()
    {
        return this.skullVariant;
    }

    @SideOnly(Side.CLIENT)
    public int getSkullRotation()
    {
        return this.skullRotation;
    }
    
    public void setSkullRotation(int rotation)
    {
        this.skullRotation = rotation;
    }
    
    public void mirror(Mirror mirrorIn)
    {
        if (this.world != null && this.world.getBlockState(this.getPos()).getValue(BlockSkull.FACING) == EnumFacing.UP)
        {
            this.skullRotation = mirrorIn.mirrorRotation(this.skullRotation, 16);
        }
    }

    public void rotate(Rotation rotationIn)
    {
        if (this.world != null && this.world.getBlockState(this.getPos()).getValue(BlockSkull.FACING) == EnumFacing.UP)
        {
            this.skullRotation = rotationIn.rotate(this.skullRotation, 16);
        }
    }
}
