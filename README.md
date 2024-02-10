# Classes

This plugin adds a class system to your Minecraft server. You can make your own classes with customizable modifiers and perks. Level up in your classes and get ready!

## Features
- Custom class system
- Custom modifiers and perks

## Configuration

There is two files for configuration. `lang.yml` to customize the messages and `config.yml` to configure the plugin. You can see the full commented versions at the
[GitHub repository](https://github.com/efekos/Classes).

## Commands

| Command                                  | Description                                                                                       | Permission Needed                |
|------------------------------------------|---------------------------------------------------------------------------------------------------|----------------------------------|
| /class \<class> block enchantment add    | Block an enchantment for a class                                                                  | classes.block.enchantment.add    |
| /class \<class> block enchantment remove | Block an enchantment for a class                                                                  | classes.block.enchantment.remove |
| /class \<class> block enchantment list   | Block an enchantment for a class                                                                  | classes.block.enchantment.list   |
| /class \<class> block material add       | Block a material (item or block) for a class                                                      | classes.block.material.add       |
| /class \<class> block material remove    | Block a material (item or block) for a class                                                      | classes.block.material.remove    |
| /class \<class> block material list      | Block a material (item or block) for a class                                                      | classes.block.material.list      |
| /class \<class> block potion add         | Block a potion effect type for a class                                                            | classes.block.potion.add         |
| /class \<class> block potion remove      | Block a potion effect type for a class                                                            | classes.block.potion.remove      |
| /class \<class> block potion list        | Block a potion effect type for a class                                                            | classes.block.potion.list        |
| /class \<class> modifier add             | Add a modifier to a class                                                                         | classes.modifier.add             |
| /class \<class> modifier remove          | Add a modifier to a class                                                                         | classes.modifier.remove          |
| /class \<class> modifier list            | Add a modifier to a class                                                                         | classes.modifier.list            |
| /class \<class> perk add                 | Add a perk to a class                                                                             | classes.perk.add                 |
| /class \<class> perk remove              | Add a perk to a class                                                                             | classes.perk.remove              |
| /class \<class> perk list                | Add a perk to a class                                                                             | classes.perk.list                |
| /class \<class> delete                   | Delete a class                                                                                    | classes.delete                   |
| /class \<class> info                     | Information about a class                                                                         | classes\.info                    |
| /class \<class> join                     | Join to a class                                                                                   | classes.join                     |
| /class \<class\> kit get                 | Get the kit of your class. Players with permission `classes.admin` get any kit using this command | classes.kit.get                  |
| /class \<class\> kit update              | Update the contents of a class kit                                                                | classes.kit.update               |
| /class \<class\> kit clear               | Clear a class kit                                                                                 | classes.kit.clear                |
| /class \<class> members                  | See all the members in a class                                                                    | classes.members                  |
| /class set criteria                      | Change the criteria, the way of leveling up at a class                                            | classes.set.criteria             |
| /class set description                   | Change the description of a class                                                                 | classes.set.description          |
| /class set icon                          | Change the icon of a class                                                                        | classes.set.icon                 |
| /class create                            | Create a new class                                                                                | classes.create                   |
| /class leave                             | Leave a class                                                                                     | classes.leave                    |
| /class choose                            | Choose a class                                                                                    | classes.choose                   |

## Permissions

Most of the permissions are already given at [commands](#commands) section. This plugin also supports permission parenting.
You can use `*` character to select children permissions (classes.\*, classes.block.\*, classes.block.perk.\* etc.). There are
still two permissions left to explain:

| Permission            | Description                                              |
|-----------------------|----------------------------------------------------------|
| classes.use           | Main permission required to run any command.             |
| classes.admin         | Allows you to get any kit using /class \<class\> kit get |
