package br.lucas.superpowers;

import com.mojang.brigadier.Command;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import static net.minecraft.server.command.CommandManager.literal;

public class SuperPowers implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("sp")
                .then(literal("fly").executes(ctx -> {
                    ServerPlayerEntity p = ctx.getSource().getPlayerOrThrow();
                    boolean enable = !p.getAbilities().allowFlying;
                    p.getAbilities().allowFlying = enable;
                    p.getAbilities().flying = enable;
                    if (!enable) p.getAbilities().flying = false;
                    p.sendAbilitiesUpdate();
                    p.sendMessage(Text.literal("SuperPowers: voo " + (enable ? "ativado" : "desativado")), false);
                    return Command.SINGLE_SUCCESS;
                }))
                .then(literal("jump").executes(ctx -> {
                    ServerPlayerEntity p = ctx.getSource().getPlayerOrThrow();
                    Vec3d v = p.getVelocity();
                    p.setVelocity(v.x, 1.35, v.z);
                    p.velocityModified = true;
                    p.sendMessage(Text.literal("SuperPowers: superpulo!"), false);
                    return Command.SINGLE_SUCCESS;
                }))
                .then(literal("haste").executes(ctx -> {
                    ServerPlayerEntity p = ctx.getSource().getPlayerOrThrow();
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 20 * 60 * 10, 4));
                    p.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 20 * 60 * 10, 1));
                    p.sendMessage(Text.literal("SuperPowers: quebra rapida ativada por 10 minutos!"), false);
                    return Command.SINGLE_SUCCESS;
                }))
                .then(literal("lightning").executes(ctx -> {
                    ServerPlayerEntity p = ctx.getSource().getPlayerOrThrow();
                    ServerWorld world = p.getServerWorld();
                    BlockPos pos = p.getBlockPos().offset(p.getHorizontalFacing(), 4);
                    LightningEntity bolt = EntityType.LIGHTNING_BOLT.create(world, SpawnReason.TRIGGERED);
                    if (bolt != null) {
                        bolt.refreshPositionAfterTeleport(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                        world.spawnEntity(bolt);
                    }
                    p.sendMessage(Text.literal("SuperPowers: raio invocado!"), false);
                    return Command.SINGLE_SUCCESS;
                }))
                .then(literal("boom").executes(ctx -> {
                    ServerPlayerEntity p = ctx.getSource().getPlayerOrThrow();
                    ServerWorld world = p.getServerWorld();
                    Vec3d look = p.getRotationVec(1.0F);
                    Vec3d pos = p.getEyePos().add(look.multiply(5.0));
                    world.createExplosion(p, pos.x, pos.y, pos.z, 2.5F, World.ExplosionSourceType.TNT);
                    p.sendMessage(Text.literal("SuperPowers: explosao criada!"), false);
                    return Command.SINGLE_SUCCESS;
                }))
                .then(literal("help").executes(ctx -> {
                    ctx.getSource().sendFeedback(() -> Text.literal(
                        "SuperPowers: /sp fly | /sp jump | /sp haste | /sp lightning | /sp boom"
                    ), false);
                    return Command.SINGLE_SUCCESS;
                }))
            );
        });
    }
}
