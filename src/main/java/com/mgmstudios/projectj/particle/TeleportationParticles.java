package com.mgmstudios.projectj.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import org.jetbrains.annotations.Nullable;

public final class TeleportationParticles extends TextureSheetParticle {
    public TeleportationParticles(ClientLevel clientLevel, double x, double y, double z, SpriteSet spriteSet, double xSpeed, double ySpeed, double zSpeed) {
        super(clientLevel, x, y, z, xSpeed, ySpeed, zSpeed);

        this.lifetime = 40;
        this.xd = 0;
        this.yd = 0;
        this.zd = 0;
        this.setSpriteFromAge(spriteSet);
    }

    @Override
    public void setSpriteFromAge(SpriteSet sprite) {
        super.setSpriteFromAge(sprite);
    }

    @Override
    public void tick() {
        super.tick();
        this.setAlpha(1 - (float) this.age / this.lifetime);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static final class Provider implements ParticleProvider<SimpleParticleType>{
       private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSet) {
            this.spriteSet = spriteSet;
        }

        @Override
        public @Nullable Particle createParticle(SimpleParticleType simpleParticleType, ClientLevel clientLevel, double px, double py, double pz, double pxSpeed, double pySpeed, double pzSpeed) {
            return new TeleportationParticles(clientLevel, px, py, pz, this.spriteSet, pxSpeed, pySpeed, pzSpeed);
        }
    }
}
