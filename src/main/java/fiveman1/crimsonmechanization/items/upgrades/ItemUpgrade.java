package fiveman1.crimsonmechanization.items.upgrades;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.util.IInitializeable;
import fiveman1.crimsonmechanization.util.RegistryHandler;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemUpgrade extends Item implements IInitializeable {

    protected EnumUpgrade upgradeType;

    public ItemUpgrade(EnumUpgrade enumUpgrade) {
        upgradeType = enumUpgrade;
        setRegistryName(CrimsonMechanization.MODID, "upgrade_" +  upgradeType.resourceName);
        setUnlocalizedName("upgrade" + upgradeType.unlocalizedName);
        RegistryHandler.INITIALIZEABLES.add(this);
    }

    @Override
    public void initItem(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initModel(ModelRegistryEvent event) {
        ResourceLocation location = new ResourceLocation(CrimsonMechanization.MODID, "upgrade");
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(location, "type=" + upgradeType.resourceName));
    }

    public enum EnumUpgrade {
        SPEED("speed", "Speed"),
        EFFICIENCY("efficiency", "Efficiency"),
        LUCK("luck", "Luck")
        ;

        public final String resourceName;
        public final String unlocalizedName;

        EnumUpgrade(String resourceName, String unlocalizedName) {
            this.resourceName = resourceName;
            this.unlocalizedName = unlocalizedName;
        }

    }
}
