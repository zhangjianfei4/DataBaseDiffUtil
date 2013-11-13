package com.hskj.main;
import java.util.List;

import com.hskj.dbunit.Column;
import com.hskj.dbunit.DBComponent;
import com.hskj.dbunit.Schemata;
import com.hskj.dbunit.Table;


public class Comparation {
	private boolean _columnComparing(Table table_base,Table table_other){
		for(Column column_base:table_base.getColumnList()){
			Column column_other = table_other.getColumnByName(column_base.getTable_name(),column_base.getColumn_name());
			if(column_other!=null){
				//比较属性
				if(_columnProComparing(column_base,column_other)){
					table_other.setModified(true);
				}
			}else{
				//从表中被删减
				table_other.getRm_list().add(column_base);
				table_other.setModified(true);
			}
		}
		//反向比较
		for(Column column_other:table_other.getColumnList()){
			Column column_base = table_base.getColumnByName(column_other.getTable_name(),column_other.getColumn_name());
			if(column_base!=null){
			}else{
				//从表多出属性
				column_other.setIs_extra_column(true);
				table_other.setModified(true);
			}
		}
		return table_other.isModified();
	}
	/**
	 * 
	 * @param base
	 * @param other
	 * @return true:有变化 false无变化
	 */
	private boolean _columnProComparing(Column base, Column other) {
		if (base.getCharacter_maximum_length() == null) {
			if (other.getCharacter_maximum_length() != null){
				other.getErr_filed().add("character_maximum_length");
			}
		} else if (!base.getCharacter_maximum_length()
				.equals(other.getCharacter_maximum_length())){
			other.getErr_filed().add("character_maximum_length");
		}
		if (base.getCharacter_set_name() == null) {
			if (other.getCharacter_set_name() != null){
				other.getErr_filed().add("character_set_name");
			}
		} else if (!base.getCharacter_set_name().equals(other.getCharacter_set_name())){
			other.getErr_filed().add("character_set_name");
		}
		if (base.getCollation_name() == null) {
			if (other.getCollation_name()!= null){
				other.getErr_filed().add("collation_name");
			}
		} else if (!base.getCollation_name().equals(other.getCollation_name())){
			other.getErr_filed().add("collation_name");
		}
		if (base.getColumn_default() == null) {
			if (other.getColumn_default() != null){
				other.getErr_filed().add("column_default");
			}
		} else if (!base.getColumn_default().equals(other.getColumn_default())){
			other.getErr_filed().add("column_default");
		}
		if (base.getColumn_key() == null) {
			if (other.getColumn_key() != null){
				other.getErr_filed().add("column_key");
			}
		} else if (!base.getColumn_key().equals(other.getColumn_key())){
			other.getErr_filed().add("column_key");
		}
		if (base.getColumn_name() == null) {
			if (other.getColumn_name() != null){
				other.getErr_filed().add("column_name");
			}
		} else if (!base.getColumn_name().equals(other.getColumn_name())){
			other.getErr_filed().add("column_name");
		}
		if (base.getColumn_type() == null) {
			if (other.getColumn_type() != null){
				other.getErr_filed().add("column_type");
			}
		} else if (!base.getColumn_type().equals(other.getColumn_type())){
			other.getErr_filed().add("column_type");
		}
		if (base.getData_type() == null) {
			if (other.getData_type() != null)
				other.getErr_filed().add("data_type");
		} else if (!base.getData_type().equals(other.getData_type())){
			other.getErr_filed().add("data_type");
		}
		if (base.getExtra() == null) {
			if (other.getExtra() != null){
				other.getErr_filed().add("extra");
			}
		} else if (!base.getExtra().equals(other.getExtra())){
			other.getErr_filed().add("extra");
		}
		if (base.getIs_nullable() == null) {
			if (other.getIs_nullable() != null){
				other.getErr_filed().add("is_nullable");
			}
		} else if (!base.getIs_nullable().equals(other.getIs_nullable())){
			other.getErr_filed().add("is_nullable");
		}
		if (base.getOrdinal_position() != other.getOrdinal_position()){
			other.getErr_filed().add("ordinal_position");
		}
		if (base.getTable_name() == null) {
			if (other.getTable_name() != null)
				other.getErr_filed().add("table_name");
		} else if (!base.getTable_name().equals(other.getTable_name())){
			other.getErr_filed().add("table_name");
		}
		if(other.getErr_filed()!=null&&other.getErr_filed().size()>0){
			other.setBase_column(base);
			return true;
		}
		return false;
	}
	public void _getDiffMessage(DBComponent component){
		System.out.println();
		System.out.println("-------------------------"+component.getDbname()+"----------------------------");
		for(String e:component.getSchemata().getErr_filed()){
			System.out.println("*属性差异："+e);
		}
		for(Table t:component.getRm_list()){
			System.out.println("-表："+t.getTABLE_NAME());
		}
		for(Table t :component.getTableList()){
			System.out.println("-------------------------"+t.getTABLE_NAME()+"----------------------------");
			if(t.isIs_extra_table()){
				System.out.println("此表为多出的表");
				break;
			}
			for(Column c:t.getRm_list()){
				System.out.println("-缺少列："+c.getColumn_name());
			}
			for(Column c:t.getColumnList()){
				if(c.isIs_extra_column()){
					System.out.println("+多出的列"+c.getColumn_name());
				}
			}
			for(Column c:t.getColumnList()){
				if(c.getErr_filed().size()>0){
					System.out.println("字段"+c.getColumn_name()+"列属性不同的数量："+c.getErr_filed());
				}
			}
		}
	}
	private static void _schemataProComparing(Schemata base,Schemata other){
		if (base.getDEFAULT_CHARACTER_SET_NAME() == null) {
			if (other.getDEFAULT_CHARACTER_SET_NAME() != null)
				other.getErr_filed().add("DEFAULT_CHARACTER_SET_NAME");
		} else if (!base.getDEFAULT_CHARACTER_SET_NAME()
				.equals(other.getDEFAULT_CHARACTER_SET_NAME())){
			other.getErr_filed().add("DEFAULT_CHARACTER_SET_NAME");
		}
		if (base.getDEFAULT_COLLATION_NAME() == null) {
			if (other.getDEFAULT_COLLATION_NAME() != null){
				other.getErr_filed().add("DEFAULT_COLLATION_NAME");
			}
		} else if (!base.getDEFAULT_COLLATION_NAME().equals(other.getDEFAULT_COLLATION_NAME())){
			other.getErr_filed().add("DEFAULT_COLLATION_NAME");
		}
	}
	public String _start(DBComponent base,DBComponent other){
		_schemataProComparing(base.getSchemata(),other.getSchemata());
		for(Table table_base:base.getTableList()){
			Table table_other = other.getTableByTableName(table_base.getTABLE_NAME());
			if(table_other!=null){
				//比较表属性
				if(_tableProComparing(table_base,table_other)){
					other.setModified(true);
				}
				if(_columnComparing(table_base,table_other)){
					other.setModified(true);
				}
			}else{
				//从表中被删减的表
				other.getRm_list().add(table_base);
				other.setModified(true);
			}
		}
		//反向比较
		for(Table table_other:other.getTableList()){
			Table table_base = base.getTableByTableName(table_other.getTABLE_NAME());
			if(table_base!=null){
				//主表存在
			}else{
				//从表中多出的表
				table_other.setIs_extra_table(true);
				other.setModified(true);
			}
		}
		return null;
	}
	public void _compare(DBComponent compareTable,List<DBComponent> compareTables){
		if(compareTables!=null && compareTables.size()>0){
			for(DBComponent c:compareTables){
				_start(compareTable,c);
				_getDiffMessage(c);
			}
		}
	}
	private boolean _tableProComparing(Table base,Table other){
		if (base.getENGINE()== null) {
			if (other.getENGINE() != null){
				other.getErr_filed().add("ENGINE");
			}
		} else if (!base.getENGINE().equals(other.getENGINE())){
			other.getErr_filed().add("ENGINE");
		}
		if (base.getTABLE_NAME() == null) {
			if (other.getTABLE_NAME() != null){
				other.getErr_filed().add("TABLE_NAME");
			}
		} else if (!base.getTABLE_NAME().equals(other.getTABLE_NAME())){
			other.getErr_filed().add("TABLE_NAME");
		}
		if(other.getErr_filed()!=null&&other.getErr_filed().size()>0){
			other.setBase_table(base);
			other.setModified(true);
		}
		if(other.isModified()){
			return true;
		}else{
			return false;
		}
	}
	
	
	
	
	
	public static void main(String[] args) throws Exception{
		
		DBComponent compareTable = new DBComponent("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=gbk","root","root");
		
		
		DBComponent compareTable2 = new DBComponent("jdbc:mysql://localhost:3306/test2?useUnicode=true&characterEncoding=gbk","root","root");
		
		Comparation c = new Comparation();
		c._start(compareTable,compareTable2);
		c._getDiffMessage(compareTable2);
	}
}
