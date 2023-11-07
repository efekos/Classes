# Classes

This plugin adds a class system to your Minecraft server. You can make your own classes with customizable modifiers and perks. Level up in your classes and get ready!

## Features
- Custom class system
- Custom modifiers and perks

## Configuration

There is two files for configuration. `lang.yml` to customize the messages and `config.yml` to configure the plugin. You can see the full commented versions at the
[Github repository](https://github.com/efekos/Classes).

## Commands

| Command                       | Description                                                                                       | Permission Needed               |
|-------------------------------|---------------------------------------------------------------------------------------------------|---------------------------------|
| /class addenchantmentblock    | Block an enchantment for a class                                                                  | classes.addblock.enchantment    |
| /class addmaterialblock       | Block a material (item or block) for a class                                                      | classes.addblock.material       |
| /class addpotionblock         | Block a potion effect type for a class                                                            | classes.addblock.potion         |
| /class addmodifier            | Add a modifier to a class                                                                         | classes.addmodifier             |
| /class addperk                | Add a perk to a class                                                                             | classes.addperk                 |
| /class create                 | Create a new class                                                                                | classes.create                  |
| /class delete                 | Delete a class                                                                                    | classes.delete                  |
| /class info                   | Information about a class                                                                         | classes.info                    |
| /class join                   | Join to a class                                                                                   | classes.join                    |
| /class kit \<class\> get      | Get the kit of your class. Players with permission `classes.admin` get any kit using this command | classes.kit.get                 |
| /class kit \<class\> update   | Update the contents of a class kit                                                                | classes.kit.update              |
| /class kit \<class\> clear    | Clear a class kit                                                                                 | classes.kit.clear               |
| /class leave                  | Leave a class                                                                                     | classes.leave                   |
| /class members                | See all the members in a class                                                                    | classes.members                 |
| /class modifiers              | See all the modifiers in a class                                                                  | classes.modifiers               |
| /class perks                  | See all the perks in a class                                                                      | class.perks                     |
| /class removeenchantmentblock | Remove a enchantment from the blocklist of a class                                                | classes.removeblock.enchantment |
| /class removematerialblock    | Remove a material from the blocklist of a class                                                   | classes.removeblock.material    |
| /class removepotionblock      | Remove a potion from the blocklist of a class                                                     | classes.removeblock.potion      |
| /class removemodifier         | Remove a modifier from a class                                                                    | classes.removemodifier          |
| /class removeperk             | Remove a perk from a class                                                                        | classes.removeperk              |
| /class setcriteria            | Change the criteria, the way of leveling up at a class                                            | classes.setcriteria             |
| /class setdescription         | Change the description of a class                                                                 | classes.setdescription          |
| /class seticon                | Change the icon of a class                                                                        | classes.seticon                 |

## Permissions

Most of the permissions are already given at [commands](#commands) section. Here is the rest of the permissions

| Permission            | Description                                              |
|-----------------------|----------------------------------------------------------|
| classes.*             | Every permission under classes                           |
| classes.kit.*         | Every permission under classes.kit                       |
| classes.addblock.*    | Every permission under classes.addblock                  |
| classes.removeblock.* | Every permission under classes.removeblock               |
| classes.admin         | Allows you to get any kit using /class kit \<class\> get |
