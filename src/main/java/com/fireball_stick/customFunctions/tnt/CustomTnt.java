package com.fireball_stick.customFunctions.tnt;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class CustomTnt extends PrimedTnt {
    public CustomTnt(EntityType<? extends CustomTnt> type, Level level) {
        super(type, level);
    }
//Ability to separate the values for the explosion power of TNTs for different classes

    private final List<Runnable> QUEUE = new ArrayList<>();
    private int ticksSinceExplosion = 0;
    public void add(Runnable task) {
        QUEUE.add(task);
    }

    float explosionPower = 4.0F; //Default: 4.0F
    double defaultGravity = 0.04; //Default: 0.04
    //CUSTOM-MADE

    //onGround makes the primedTNT only explode when it hits a horizontal surface, not a vertical surface
    boolean explodeOnContact = false;
    EntityType<?> entityToSpawn = EntityType.CHICKEN;
    int entityAmount = 5;
    boolean entitySpawnAfterExplosion = false;
    boolean isCircle = true;
    double xChange = 0;
    double yChange = 0;
    double zChange = 0;
    double amplitude = 10;
    boolean afterSpawnEffects = false;
    int initialExplosionDelayCounter = 40; //Ticks
    int afterFirstExplosionDelay = 5; //Ticks
    boolean individualExplosions = true;
    boolean discardTNT = true;
    boolean exploded = false;

    protected void explode() {
        level().explode(
                this,
                getX(),
                getY(),
                getZ(),
                explosionPower,
                Level.ExplosionInteraction.TNT
        );
        if(discardTNT) {
            exploded = false;
        }
    }

    @Override
    public void tick() {
        //Inherits logic from tick(), where we only override what's specified under. Otherwise, we have to put *all* the logic that tick() uses here
        super.tick();

        if(shouldExplode() && !exploded) {
            discardOnFirstUse();
            explode();
            onPostExplode();
            onPreExplode();
            ticksSinceExplosion = 0;
        } else if(exploded) {
            ticksSinceExplosion++;
            onPostEntitySpawning();
        }
    }

    protected boolean shouldExplode() {
        return ((getFuse() <= 0 && !level().isClientSide())
                || ((this.horizontalCollision || this.verticalCollision)
                && explodeOnContact));
    }

    protected void onPreExplode() {

    }
    //Might expand in the future
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
                    add(() -> {
                    if(isCircle) {
                        entity.setPos(
                                getX() + (Math.cos(angle[0]) * amplitude),
                                getY(),
                                getZ() + (Math.sin(angle[0]) * amplitude));
                        angle[0] += angleStep;
                        changePosition[0] += Math.PI / ((double) (entityAmount / 4) / 2);
                    } else {
                        entity.setPos(
                                getX() + changeX[0],
                                getY() + changeY[0],
                                getZ() + changeZ[0]);
                        changeX[0] += xChange;
                        changeY[0] += yChange;
                        changeZ[0] += zChange;
                    }
                    server.addFreshEntity(entity);
                });
                }
            }
        }
    }

    protected void onPostEntitySpawning() {

            if (afterSpawnEffects) {
                return;
            }

            if (!individualExplosions) {
                return;
                //Explode all entities immediately
            }

            initialExplosionDelayCounter++;
            if (ticksSinceExplosion >= afterFirstExplosionDelay && ticksSinceExplosion >= initialExplosionDelayCounter && !QUEUE.isEmpty()) {
                //Resets the tick counter
                ticksSinceExplosion = 0;
                QUEUE.remove(0).run();
                System.out.println("works!");
            }

            if (QUEUE.isEmpty()) {
                this.discard();
                System.out.println("what");
            }
    }

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

    public boolean getCircle() {
        return isCircle;
    }

    public void setCircle(boolean circle) {
        this.isCircle = circle;
    }

    public double getXChange() {
        return xChange;
    }

    public double getAmplitude() {
        return this.amplitude;
    }

    public void setAmplitude(double amp) {
        this.amplitude = amp;
    }

    public void setXChange(double x) {
        this.xChange = x;
    }

    public double getYChange() {
        return yChange;
    }

    public void setYChange(double y) {
        this.yChange = y;
    }

    public double getZChange() {
        return zChange;
    }

    public void setZChange(double z) {
        this.zChange = z;
    }

    public boolean getDiscardOnFirstUse() {
        return this.discardTNT;
    }

    public void setDiscardOnFirstUse(boolean discard) {
        this.discardTNT = discard;
    }

    public boolean getAfterSpawnEffects() {
        return afterSpawnEffects;
    }

    public void setAfterSpawnEffects(boolean afterSpawnEffects) {
        this.afterSpawnEffects = afterSpawnEffects;
    }
}

//TODO:
//Add an option to adjust the pushback primed TNT get from exploding close to each other
//Make a class CustomTntChicken extend this class, to change its attributes for separate functionality for TNTStickClickBlock
//Make the TNT able to explode in the air, maybe after some delay
//Maybe a limit to how many times a TNT can explode as a fall-back
