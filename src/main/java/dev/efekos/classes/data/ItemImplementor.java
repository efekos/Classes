package dev.efekos.classes.data;

import dev.efekos.simple_ql.data.GetterAction;
import dev.efekos.simple_ql.data.SetterAction;
import dev.efekos.simple_ql.implementor.Implementor;
import net.md_5.bungee.api.chat.ItemTag;
import net.md_5.bungee.api.chat.hover.content.Item;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ItemImplementor implements Implementor<Item,String> {

    public static final ItemImplementor INSTANCE = new ItemImplementor();
    public static final String SEPARATOR_CHAR = "\uE978";

    @Override
    public String write(Item item) {
        return item.getId()+SEPARATOR_CHAR+item.getTag().getNbt()+SEPARATOR_CHAR+item.getCount();
    }

    @Override
    public Item read(String s) {
        String[] ss = s.split(SEPARATOR_CHAR);
        return new Item(ss[0],Integer.parseInt(ss[2]), ItemTag.ofNbt(ss[1]));
    }

    @Override
    public SetterAction<String> setter() {
        return PreparedStatement::setString;
    }

    @Override
    public GetterAction<String> getter() {
        return ResultSet::getString;
    }

    @Override
    public String type() {
        return "TEXT";
    }

}
