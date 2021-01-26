package fiveman1.crimsonmechanization.datagen;

import fiveman1.crimsonmechanization.CrimsonMechanization;
import fiveman1.crimsonmechanization.blocks.BlockRegistration;
import fiveman1.crimsonmechanization.blocks.MachineBlock;
import fiveman1.crimsonmechanization.enums.MachineTier;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, ExistingFileHelper fileHelper) {
        super(gen, CrimsonMechanization.MODID, fileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerMachineBases();
        registerMachines(BlockRegistration.compactorMachineBlockCrimson, BlockRegistration.compactorMachineBlockRefined, BlockRegistration.compactorMachineBlockIridescent);
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
                    .texture("west", side);
        }
    }

    private void registerMachines(MachineBlock... blocks) {
        for (MachineBlock block : blocks) {
            String name = block.getMachineName();
            ResourceLocation textureOff = new ResourceLocation(CrimsonMechanization.MODID, "block/" + name + "_off");
            ResourceLocation textureOn = new ResourceLocation(CrimsonMechanization.MODID, "block/" + name + "_on");
            ResourceLocation parent = new ResourceLocation(CrimsonMechanization.MODID, "machine_" + block.getTier().getName());
            BlockModelBuilder modelOff = models().withExistingParent(name, parent).texture("north", textureOff);
            BlockModelBuilder modelOn = models().withExistingParent(name + "_active", parent).texture("north", textureOn);
            horizontalBlock(block, state -> state.get(MachineBlock.ACTIVE) ? modelOn : modelOff);
        }
    }
}
