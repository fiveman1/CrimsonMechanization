package fiveman1.crimsonmechanization.blocks.materials;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.datagen.tags.ModBlockTags;
import fiveman1.crimsonmechanization.datagen.tags.ModItemTags;
import fiveman1.crimsonmechanization.enums.BaseMaterial;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;

public class MaterialBlock extends Block {

    public final String name;
    public final BaseMaterial material;

    public MaterialBlock(String blockName, BaseMaterial material) {
        super(AbstractBlock.Properties.create(Material.IRON)
            .harvestTool(ToolType.PICKAXE)
            .harvestLevel(material.harvestLevel)
            .hardnessAndResistance(material.hardness, material.resistance)
        );
        setRegistryName(CrimsonMechanization.MODID, blockName + "_" + material.name);
        this.name = blockName;
        this.material = material;
        ModBlockTags.createForgeTag(this, getBlockTagName() + "s/" + material.name);
        ModBlockTags.createForgeTag(this, getBlockTagName() + "s");
    }

    public String getItemTagName() {
        return name;
    }

    public String getBlockTagName() {
        return name;
    }

    public Item getBlockItem(Item.Properties properties) {
        Item item = new BlockItem(this, properties);
        item.setRegistryName(getRegistryName());
        ModItemTags.createForgeTag(item, getItemTagName() + "s/" + material.name);
        ModItemTags.createForgeTag(item, getItemTagName() + "s");
        return item;
    }
}
