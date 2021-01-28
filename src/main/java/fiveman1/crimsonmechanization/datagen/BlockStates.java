package fiveman1.crimsonmechanization.datagen;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import fiveman1.crimsonmechanization.blocks.AbstractMachineBlock;
import fiveman1.crimsonmechanization.enums.MachineTier;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.List;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, ExistingFileHelper fileHelper) {
        super(gen, CrimsonMechanization.MODID, fileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerMachineBases();
        registerMachines();
        registerSimple(BlockRegistration.MATERIAL_BLOCKS);
    }

    private void registerMachineBases() {
        for (MachineTier tier : MachineTier.values) {
            String name = "machine_" + tier.getName();
            ResourceLocation top = new ResourceLocation(CrimsonMechanization.MODID, "block/" + name + "_top");
            ResourceLocation bottom = new ResourceLocation(CrimsonMechanization.MODID, "block/" + name + "_bottom");
            ResourceLocation side = new ResourceLocation(CrimsonMechanization.MODID, "block/" + name + "_side");
            models().getBuilder(name).parent(models().getExistingFile(new ResourceLocation("minecraft", "block/cube")))
                    .texture("down", bottom)
                    .texture("up", top)
                    .texture("south", side)
                    .texture("east", side)
                    .texture("west", side)
                    .texture("particle", side);
        }
    }

    private void registerMachines() {
        for (AbstractMachineBlock block : BlockRegistration.MACHINES) {
            String name = block.getRegistryName().getPath();
            ResourceLocation textureOff = new ResourceLocation(CrimsonMechanization.MODID, "block/" + name + "_off");
            ResourceLocation textureOn = new ResourceLocation(CrimsonMechanization.MODID, "block/" + name + "_on");
            ResourceLocation parent = new ResourceLocation(CrimsonMechanization.MODID, "machine_" + block.getTier().getName());
            BlockModelBuilder modelOff = models().withExistingParent(name, parent).texture("north", textureOff);
            BlockModelBuilder modelOn = models().withExistingParent(name + "_active", parent).texture("north", textureOn);
            horizontalBlock(block, state -> state.get(AbstractMachineBlock.ACTIVE) ? modelOn : modelOff);
        }
    }

    private void registerSimple(List<? extends Block> blocks) {
        for (Block block : blocks) {
            simpleBlock(block);
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return "Crimson Mechanization Block States";
    }
}
