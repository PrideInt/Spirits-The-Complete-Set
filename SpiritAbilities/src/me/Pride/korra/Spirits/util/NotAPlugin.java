package me.Pride.korra.Spirits.util;

import com.projectkorra.projectkorra.ProjectKorra;
import com.projectkorra.projectkorra.configuration.ConfigManager;
import me.Pride.korra.Spirits.listener.AbilListener;
import me.numin.spirits.SpiritElement;

public class NotAPlugin {

    private static boolean initialized = false;

    public static void initialize() {
        if (initialized) return;

        ProjectKorra.log.info("SPIRITS: THE COMPLETE SET BY PRRIDE LOADED!");
        ProjectKorra.plugin.getServer().getPluginManager().registerEvents(new AbilListener(), ProjectKorra.plugin);

        //Neutral - Float
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Neutral.Float.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Neutral.Float.Cooldown", 7000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Neutral.Float.FloatDuration", 4500);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Neutral.Float.FloatPower", 1);
        ConfigManager.languageConfig.get().addDefault("Abilities.Spirit.Float.Description", "Some spirits are able to levitate and even fly through the air! The physiology of these spirits allow them to float for a while.");
        ConfigManager.languageConfig.get().addDefault("Abilities.Spirit.Float.Instructions", "To use this ability, left click and you are able to float.");

        //Dark
        //Corruption
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Corruption.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Corruption.Cooldown", 12000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Corruption.Radius", 6);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Corruption.Duration", 15);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Corruption.EffectDuration", 2);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Corruption.EffectAmplifier", 1);
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.Corruption.Description", "Dark spirits are entities filled with rage and malevolence. "
                + "They are able to infect and influence the area around them and imbue their negative "
                + "energies to it. They could also summon more dark spirits within this area in order to spread their corruption. "
                + "Mobs and land are also affected in this area of influence.");
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.Corruption.Instructions", "To use, hold sneak.");

        //DarkBeam
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.DarkBeam.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.DarkBeam.Cooldown", 7000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.DarkBeam.ChargeTime", 4000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.DarkBeam.Duration", 2000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.DarkBeam.Damage", 2);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.DarkBeam.Range", 20);
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.DarkBeam.Description", "By channeling all the stored energy within the bodies of "
                + "Dark spirits, they are able to release it in the form of a deadly beam!");
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.DarkBeam.Instructions", "To use, hold sneak until purple spell particles appear and left click.");

        //Onslaught
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Onslaught.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Onslaught.Cooldown", 7500);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Onslaught.Speed", 0.4);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Onslaught.EffectAmplifier", 1);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Onslaught.EffectDuration", 1.5);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Onslaught.Duration", 1);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Onslaught.Damage", 3);
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.Onslaught.Description", "Dark spirits are able to charge and assault their "
                + "victims, oftentimes badly deforming and corrupting them!");
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.Onslaught.Instructions", "To use, hold sneak and left click.");

        //Shadow
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Shadow.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Shadow.Cooldown", 10000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Shadow.Duration", 500);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Shadow.CollisionRadius", 1);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Dark.Shadow.TeleportRange", 8);
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.Shadow.Description", "Dark spirits are able to travel quickly in the darkness and render themselves "
                + "permeable for a split second to any attack by transforming into the night! When in shadow mode however, spirits are unable "
                + "to see due to transforming into pure darkness.");
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.Shadow.Instructions", "To use, hold sneak. While holding sneak and in shadow mode temporarily, release to teleport. "
                + "(Note: This is quick use ability, all effects are to be used within that split second of shadow mode)");

        //Light Abilities
        //Enlightenment
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.Cooldown", 10000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.ChargeTime", 4.5);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.EnlightenRadius", 1.5);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.EffectAmplifier", 3);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.EffectDuration", 12);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.AbsorptionHealth", 3);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.Forcefield.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.Forcefield.ShieldRadius", 3.5);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.Forcefield.Damage", 3);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Enlightenment.Forcefield.Repel", 0.4);
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Enlightenment.DeathMessage", "{victim} was repelled by {attacker}'s {ability} shield!");
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Enlightenment.Description", "Enlightenment allows the user to gain buffs and positive effects "
                + "through the use of spiritual knowledge! With the help of other spirits and light spirits, buffs are "
                + "more stronger and effective and you are able to share your enlightenment! After gaining enlightenment, your light "
                + "attacks become stronger and you produce a temporary forcefield to ward out dark spirits.");
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Enlightenment.Instructions", "To use, hold sneak until a certain time and release. If close to other spirits or light spirits, "
                + "your buffs increase and you are able to enlighten them as well.");

        //LightBeam
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.LightBeam.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.LightBeam.Cooldown", 7000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.LightBeam.ChargeTime", 4000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.LightBeam.Duration", 2000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.LightBeam.Damage", 2);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.LightBeam.Range", 20);
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.LightBeam.Description", "By channeling all the stored energy within the bodies of "
                + "Light spirits, they are able to release it in the form of a bright beam!");
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.LightBeam.Instructions", "To use, hold sneak until white spell particles appear and left click.");

        //Safeguard
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Safeguard.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Safeguard.Cooldown", 14000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.LightBeam.Duration", 10000);
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Safeguard.Description", "By creating a shield of positive light energy around themselves, "
                + "Light spirits are able to protect themselves from negative status effects with positive status effects.");
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Safeguard.Instructions", "To use, tap sneak.");

        //Wish
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Wish.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Wish.Cooldown", 7000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Wish.ChargeTime", 4500);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Wish.WaitDuration", 15000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Abilities.Light.Wish.HealAmount", 4);
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Safeguard.Description", "By wishing and having a positive mind, "
                + "light spirits are able to harness and channel their positive energies to be able to heal from wounds.");
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Safeguard.Instructions", "To use, hold sneak until white spell particles appear and release");

        //Passives
        //AfterGlow (light)
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Light.Afterglow.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Light.Afterglow.Cooldown", 8000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Light.Afterglow.HealAmount", 3);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Light.Afterglow.Damage", 4);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Light.Afterglow.Duration", 20000);
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Passive.Afterglow.Description", "When Light spirits perish, they leave behind a piece of residual energy containing "
                        + "light. Any fellow Light spirits touching and absorbing this afterglow will gain back health and energy while "
                        + "any Dark spirit or creature will be hurt.");

        //WishfulThinking
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Light.WishfulThinking.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Light.WishfulThinking.EffectDuration", 6);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Light.WishfulThinking.EffectAmplifier", 0);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Light.WishfulThinking.Chance", 0.01);
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Passive.WishfulThinking.Description", "Light spirits are the embodiment of positive energy! "
                + "With the mentality of hopeful and positive thinking, they gain positive effects! "
                + "Everytime you get hit, there is a small chance of getting regeneration.");

        //DarkAlliance
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Dark.DarkAlliance.Enabled", true);
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.Passive.DarkAlliance.Description", "Dark spirits and mobs alike are mutual with one another! "
                + "Their alliance compels monsters to not target the dark spirits.");

        //SinisterAura
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Dark.SinisterAura.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Dark.SinisterAura.Cooldown", 500);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Dark.SinisterAura.Range", 3);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Dark.SinisterAura.EffectAmplifier", 1);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Dark.SinisterAura.EffectDuration", 3);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Passives.Dark.SinisterAura.Chance", 0.01);
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.Passive.SinisterAura.Description", "Dark spirits are full of negative energy that can influence their targets. "
                + "Dark spirits are able to release this energy and conjure an aura that gives negative effects to any nearby entity. "
                + "Everytime you get hit, there is a small chance of releasing an aura wave.");

        //Combos
        //Awakening
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Awakening.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Awakening.Cooldown", 26000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Awakening.Duration", 14000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Awakening.Damage", 2);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Awakening.Radius", 6);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Awakening.HealAmount", 0.5);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Awakening.AttackChance", 0.012);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Awakening.EnableParticles", true);
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Combo.Awakening.Description", "Light spirits like dark spirits are able to awaken and summon spirits "
                + "to come to their aid and protection. Light spirits are able to attack Dark spirits and other dark creatures with a "
                + "powerful beam of light! By intercepting these beams as a light spirit, you are able to gain health!");
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Combo.Awakening.Instructions", "Shelter (Tap sneak) > Alleviate (Hold sneak) > Enlightenment (Left click)");

        //Nightmare
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Nightmare.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Nightmare.Cooldown", 16000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Nightmare.Radius", 5);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Nightmare.Damage", 3);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Nightmare.EffectAmplifier", 4);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Nightmare.EffectDuration", 8000);
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.Combo.Nightmare.Description", "Dark spirits are able to infect their targets with dark energy and spread "
                + "their negative energies towards their mind. By doing so, they can cause absolute chaos towards the strengths of "
                + "all creatures with every worst status effect.");
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.Combo.Nightmare.Instructions", "Intoxicate (Hold sneak) > Onslaught (Release sneak) > Onslaught (Left click) > Shackle (Hold sneak)");

        //Pandemonium
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Pandemonium.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Pandemonium.Cooldown", 18000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Pandemonium.Duration", 10500);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Pandemonium.Radius", 10);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Pandemonium.Pull", 0.02);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Pandemonium.EffectAmplifier", 0);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Dark.Pandemonium.EffectDuration", 3);
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.Combo.Pandemonium.Description", "Dark spirits are able to use their dark influence on other creatures nearby, "
                + "slowly corrupting them by penetrating their free will and their ability to move and pulling them towards the darkness.");
        ConfigManager.languageConfig.get().addDefault("Abilities.DarkSpirit.Combo.Pandemonium.Instructions", "Intoxicate (Tap sneak) > Intoxicate (Hold sneak) > Shackle (Release sneak)");

        //Sanctuary
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Sanctuary.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Sanctuary.Cooldown", 18000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Sanctuary.Duration", 9500);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Sanctuary.Radius", 8);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Sanctuary.Repel", 0.3);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Sanctuary.Damage", 3);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Sanctuary.EffectDuration", 2.5);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Light.Sanctuary.EffectAmplifier", 1);
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Combo.Sanctuary.Description", "EXPECTO PATRONUM!!\nLight spirits are able to generate a protective " +
                "barrier that expands from their location and repel enemies away while boosting their " +
                "light spirit allies with damage protection!");
        ConfigManager.languageConfig.get().addDefault("Abilities.LightSpirit.Combo.Sanctuary.Instructions", "Alleviate (Tap sneak) > Alleviate (Hold sneak) > Shelter (Release sneak)");

        //Skyrocket
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Neutral.Skyrocket.Enabled", true);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Neutral.Skyrocket.Cooldown", 12000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Neutral.Skyrocket.RegenTime", 6000);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Neutral.Skyrocket.Speed", 5);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Neutral.Skyrocket.Damage", 3);
        ConfigManager.getConfig().addDefault("ExtraAbilities.Prride.Spirits.Combos.Neutral.Skyrocket.Radius", 2.5);
        ConfigManager.languageConfig.get().addDefault("Abilities.Spirit.Combo.Skyrocket.Description", "Spirits are able to launch themselves in the air with such high speeds and acceleration that they are able to "
                + "aggressively slam themselves on the ground to cause ruptures.");
        ConfigManager.languageConfig.get().addDefault("Abilities.Spirit.Combo.Skyrocket.Instructions", "Agility (Tap sneak) > Agility (Tap sneak) > Agility (Left click)");

        ConfigManager.defaultConfig.save();
        ConfigManager.languageConfig.save();

        initialized = true;
    }
}
