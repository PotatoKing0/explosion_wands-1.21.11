package com.fireball_stick.customFunctions.tnt;

import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.LargeSmokeParticle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.List;

public class CustomTnt extends PrimedTnt {
    public CustomTnt(EntityType<? extends CustomTnt> type, Level level) {
        super(type, level);
    }

//Ability to separate the values for the explosion power of TNTs for different classes
    float explosionPower = 4.0F; //Default: 4.0F
    double defaultGravity = 0.04; //Default: 0.04
    //CUSTOM-MADE

    //onGround makes the primedTNT only explode when it hits a horizontal surface, not a vertical surface
    boolean explodeOnContact = false;
    //Type of entity that will spawn after explosion
    EntityType<?> entityToSpawn = EntityType.CHICKEN;
    //Amount of entities spawned after explosion
    int entityAmount = 20;
    //If entities will spawn after explosion, before the primed TNT is discarded
    boolean entitySpawnAfterExplosion = false;
    //If the shape of the spawned entities resemble a circle
    boolean isCircle = true;
    //Changing x-direction
    double xChange = 0;
    //Changing y-direction
    double yChange = 0;
    //Changing z-direction
    double zChange = 0;
    //Amplitude of the circle
    double amplitude = 10;
    //If anything else happens after the primed TNT is discarded
    boolean afterSpawnEffects = false;
    //How long the delay for the after spawn effects are after the primed TNT is first discarded
    int initialExplosionDelayCounter = 40; //Ticks
    //How long the delay for the spawn effects are after the initial delay are
    int afterFirstExplosionDelay = 5; //Ticks
    //Whether the after spawn effects should explode each entity individually or every entity at once
    boolean individualEntityExplosions = true;
    //Whether the TNT should be discarded after its first use (explosion) or not
    boolean discardTNT = true;
    //If the TNT has exploded
    boolean exploded = false;
    //List<runnable>: List that stores tasks (pieces of code to run later)
    //Runnable: Functional interface with the method run()
    //Essentially stores the code in a QUEUE to be used later in onPostEntitySpawning() when !QUEUE.isEmpty
    private final List<Runnable> QUEUE = new ArrayList<>();
    //Amount of ticks since the primed TNT first exploded
    private int ticksSinceExplosion = 0;
    //Helper method, won't have to write QUEUE.add(() -> { "code" });
    //Can write add(() -> { "code" }); instead
    public void add(Runnable task) {
        QUEUE.add(task);
    }

    @Override
    public void tick() {
        //Inherits logic from tick(), where we only override what's specified under. Otherwise, we have to put *all* the logic that tick() uses here
        super.tick();

        if (shouldExplode()) {
            discardOnFirstUse();
            explode();
            onPostExplode();
        }
    }
    //If the primed TNT should explode given *these* conditions
    protected boolean shouldExplode() {
        return ((getFuse() <= 0 && !level().isClientSide())
                || ((this.horizontalCollision || this.verticalCollision)
                && explodeOnContact));
    }

    //Responsible for exploding the TNT at its current position
    protected void explode() {
        if (level() instanceof ServerLevel serverLevel) {
            level().explode(
                    this,
                    getX(),
                    getY(),
                    getZ(),
                    explosionPower,
                    Level.ExplosionInteraction.TNT
            );
            //serverLevel.sendParticles(ParticleTypes.FLAME, getX(), getY(), getZ(), 700, 3, 3, 3, 0.2);
            boolean exploded = true;
        }
    }

    //What happens before the TNT explodes
    protected void onPreExplode() {

    }
    //What happens after the explosion(s) occurs
    protected void onPostExplode() {
        if(entityToSpawn != null && level() instanceof ServerLevel server && entitySpawnAfterExplosion) {
            final double[] angle = {Math.toRadians(0)};
            double angleStep = Math.PI / ((double) entityAmount / 2);
            final double[] changePosition = {0};
            final double[] changeX = {0};
            final double[] changeY = {0};
            final double[] changeZ = {0};
            for(int i = 0; i < entityAmount; i++) {
                Entity entity = entityToSpawn.create(server, EntitySpawnReason.TRIGGERED);
                if(entity != null) {
                    if(isCircle) {
                        entity.setPos(
                                getX() + (Math.cos(angle[0]) * amplitude),
                                getY(),
                                getZ() + (Math.sin(angle[0]) * amplitude));
                        angle[0] += angleStep;
                        changePosition[0] += Math.PI / ((double) (entityAmount / 4) / 2);
                        add(() -> System.out.print("Works in loop 1!"));
                    } else {
                        entity.setPos(
                                getX() + changeX[0],
                                getY() + changeY[0],
                                getZ() + changeZ[0]);
                        changeX[0] += xChange;
                        changeY[0] += yChange;
                        changeZ[0] += zChange;
                        add(() -> System.out.print("Works in loop 2!"));
                    }
                    server.addFreshEntity(entity);
                }
            }
        }
    }
    //What happens after the explosion(s)
    /*
    protected void onPostEntitySpawning() {

        ticksSinceExplosion++;
        if(afterSpawnEffects
                && entitySpawnAfterExplosion
                && entityAmount > 0
                && ticksSinceExplosion >= afterFirstExplosionDelay
                && !QUEUE.isEmpty())
        {
            QUEUE.remove(0).run();
            System.out.println("queue not empty!");
        } else {
            this.discard();
            System.out.println("queue empty!");
        }
    }
*/
    protected void discardOnFirstUse() {
        if(discardTNT) {
            this.discard();
        }
    }

    //Getters and setters
    protected float getExplosionPower() {
        return explosionPower;
    }

    public void setExplosionPower(float power) {
        this.explosionPower = power;
    }

    //How strong the gravity of the primed TNT is
    @Override
    protected double getDefaultGravity() {
        return defaultGravity;
    }

    public void setDefaultGravity(double gravity) {
        this.defaultGravity = gravity;
    }

    //Makes the TNT explode on contact
    protected boolean getExplodeOnContact() {
        return explodeOnContact;
    }

    public void setExplodeOnContact(boolean contactExplode) {
        this.explodeOnContact = contactExplode;
    }

    //If entities will spawn after the explosion
    public boolean getEntitySpawnAfterExplosion() {
        return entitySpawnAfterExplosion;
    }

    public void setEntitySpawnAfterExplosion(boolean entitiesSpawn) {
        this.entitySpawnAfterExplosion = entitiesSpawn;
    }

    //Entity that is spawned after the explosion
    public void setEntityToSpawn(final EntityType<?> entityType) {
        this.entityToSpawn = entityType;
    }

    public EntityType<?> getEntityToSpawn() {
        return entityToSpawn;
    }
    //Entity spawn amount
    public int getEntityAmount() {
        return entityAmount;
    }

    public void setEntityAmount(int amount) {
        this.entityAmount = amount;
    }
    //If the shape of the mobs spawning is a circle or not
    public boolean getCircle() {
        return isCircle;
    }

    public void setCircle(boolean circle) {
        this.isCircle = circle;
    }
    //If circle: The amplitude of the circle
    public double getAmplitude() {
        return this.amplitude;
    }

    public void setAmplitude(double amp) {
        this.amplitude = amp;
    }

    //If not circle: change the repeating x direction values
    public double getXChange() {
        return xChange;
    }

    public void setXChange(double x) {
        this.xChange = x;
    }
    //Change the repeating y direction values
    public double getYChange() {
        return yChange;
    }

    public void setYChange(double y) {
        this.yChange = y;
    }
    //Change the repeating z direction values
    public double getZChange() {
        return zChange;
    }

    public void setZChange(double z) {
        this.zChange = z;
    }
    //If the primed tnt should be discarded when it first explodes (first use). If yes, continues exploding
    //after it's first time use, only discarded when the fuse timer = 0. If no, explodes once, is discarded afterwards.
    public boolean getDiscardOnFirstUse() {
        return this.discardTNT;
    }

    public void setDiscardOnFirstUse(boolean discard) {
        this.discardTNT = discard;
    }
/*
    //If something else will happen after the explosion (other than spawning entities)
    public boolean getAfterSpawnEffects() {
        return afterSpawnEffects;
    }

    public void setAfterSpawnEffects(boolean afterSpawnEffects) {
        this.afterSpawnEffects = afterSpawnEffects;
    }
 */
    //Individual, delayed explosions on each entity if setAfterSpawnEffects = true
    public boolean getIndividualEntityExplosions() {
        return individualEntityExplosions;
    }

    public void setIndividualEntityExplosions(boolean individualExplosions) {
        this.individualEntityExplosions = individualExplosions;
    }

    //Delay when entities will explode after the primed TNT in itself has exploded
    public int getInitialExplosionDelayCounter() {
        return initialExplosionDelayCounter;
    }

    public void setInitialExplosionDelayCounter(int explosionDelay) {
        this.initialExplosionDelayCounter = explosionDelay;
    }

    public boolean getHasExploded() {
        return exploded;
    }

    public void setHasExploded(boolean hasExploded)  {
        this.exploded = hasExploded;
    }
}

//TODO:
//Add an option to adjust the pushback primed TNT get from exploding close to each other
//Make the TNT able to explode in the air, maybe after some delay
//Maybe a limit to how many times a TNT can explode as a fall-back
//Make the final explosion for discardTNT match the explosion power before the final explosion