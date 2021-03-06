/**
 * 本包内封装了内部处理和提供给外部的类和方法，主要功能是使用DBManager，和外部的Model类
 * 管理数据库。
 * <p>
 * 之所以不使用纯粹的解析Model类内部的成员变量，是因为防止真实的成员变量名称被扰码掉，使得创建数据库发生错误。
 * </p>
 * <p>
 * 而让这种绑定的义务交给外部自行定义，具有很高的灵活性，也不会再受任何的限制，在查询/修改操作时，
 * 外部可以自己定义，因为需求是不可预测的，我们简化了一些步骤，减少了外部使用太多的数据库管理类。
 * </p>
 * 
 * 
 * How to use this framework:
 * <br/>
 * <br/>
 * <p>
 * 1、首先客户端需要有一个ContentProvider类，可以是继承自本框架的{@link org.ixming.android.model.provider.BaseDBProvider}，
 * 并在AndroidManifest.xml中定义；
 * </p>
 * <p>
 * 2、然后要有一个提供数据库表及其列名的接口/类（只需要提供表明和列名的常量类），它可能是继承自{@link android.provider.BaseColumns}:
 * <pre>
 * public interface TestTable extends BaseColumns {
 * 	String TABLE_NAME = "test";
 * 	String COL1 = "col1";
 * 	String COL2 = "col2";
 * }
 * </pre>
 * </p>
 * 
 * <p>
 * 3、与“2”中相应的Model：
 * <pre>
 * <code>@</code>Table(authority=LocalDBProvider.AUTHORITY, name=TestTable.TABLE_NAME)
 * public class TestBean implements BaseSQLiteModel {

	<code>@</code>Column(name=TestTable._ID)
	<code>@</code>PrimaryKey
	private long _id;
	
	<code>@</code>Column(name=TestTable.COL1)
	private String col1 = "TestBean";
	
	<code>@</code>Column(name=TestTable.COL2)
	private float col2 = 0.1F;
	
	...
}
 * </pre>
 * </p>
 * 
 * <p>
 * 4、在数据库创建或更新时，使用到SQL创建语句，则可以直接调用{@link DBManager#getTableCreation(Class)}；
 * </p>
 * 
 * <p>
 * 5、使用{@link DBManagerFactory#getDBManager(Context, Class)}方法获取该表的数据库管理器。
 * <br/><br/>
 * Insert操作：
 * <table style="width:100%; border:#888 solid 1px;">
 * 	<tr style="text-align:center; font-weight:bold;">
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			方法
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			方法说明
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			|- {@link DBManager#insertData(BaseSQLiteModel)}<br/>
 * 			|- {@link DBManager#insertData(BaseSQLiteModel, boolean)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			插入单条记录
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			|- {@link DBManager#insertData(java.util.Collection)}<br/>
 * 			|- {@link DBManager#insertData(java.util.Collection, boolean)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			插入记录集合
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			|- {@link DBManager#insertData(Map)}<br/>
 * 			|- {@link DBManager#insertData(java.util.Map, boolean)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			插入Map结构的记录，其中，相应对象为Value，而不是Key
 * 		</td>
 * 	</tr>
 * </table>
 * 
 * 
 * <br/><br/>
 * Query操作：
 * <table style="width:100%; border:#888 solid 1px;">
 * 	<tr style="text-align:center; font-weight:bold;">
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			方法
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			方法说明
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#queryList(org.ixming.android.sqlite.SQLiteCondition)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			根据条件查询，返回记录列表
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#queryOne(org.ixming.android.sqlite.SQLiteCondition)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			根据条件查询，返回单条记录
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#queryByPrimaryKey(Object)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			根据主键查询，只得到一条记录
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#queryById(long)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			根据主键查询，只得到一条记录（特殊地，当主键为数值类型）
 * 		</td>
 * 	</tr>
 * </table>
 * 
 * 
 * <br/><br/>
 * Update操作：
 * <table style="width:100%; border:#888 solid 1px;">
 * 	<tr style="text-align:center; font-weight:bold;">
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			方法
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			方法说明
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#updateByClause(android.content.ContentValues, org.ixming.android.sqlite.SQLiteCondition)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			调用处提供ContentValues和SQLiteCondition条件进行更新。
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#updateByColumn(android.content.ContentValues, String, String)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			调用处根据单个列的某个值进行统一更新。
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#updateList(java.util.Collection, org.ixming.android.sqlite.UpdateContentValues, org.ixming.android.sqlite.SQLiteConditionDefiner)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			批量更新。就Collection集合中的单个对象而言，根据SQLiteConditionDefiner设置条件，并使用UpdateContentValues设置更新的列-值。
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#updateListByPrimaryKey(java.util.Collection, org.ixming.android.sqlite.UpdateContentValues)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			批量更新——根据主键更新（单个对象中需要主键必须已经赋值）
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#updateByPrimaryKey(android.content.ContentValues, Object)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			批量更新——根据主键更新
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#updateById(long, android.content.ContentValues)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			批量更新——根据主键更新（特别地，主键为数值类型）
 * 		</td>
 * 	</tr>
 * </table>
 * 
 * 
 * 
 * <br/><br/>
 * Delete操作：
 * <table style="width:100%; border:#888 solid 1px;">
 * 	<tr style="text-align:center; font-weight:bold;">
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			方法
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			方法说明
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#delete(org.ixming.android.sqlite.SQLiteCondition)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			根据条件删除，不确定更新的行数，由条件决定。
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#deleteAll()}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			删除所有，相当于delete(null)
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#deleteByColumn(String, String)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			根据“某个字段=某个值”的条件进行删除
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#deleteList(java.util.Collection, org.ixming.android.sqlite.SQLiteConditionDefiner)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			批量删除，对于列表中的每一项，通过SQLiteConditionDefiner定义条件，删除记录
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#deleteListByPrimaryKey(java.util.Collection)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			批量删除，对于列表中的每一项，根据主键及其值，删除记录（单个对象中需要主键必须已经赋值）
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#deleteByPrimaryKey(Object)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			根据主键及其值，删除记录
 * 		</td>
 * 	</tr>
 * 	<tr>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			{@link DBManager#deleteById(long)}
 * 		</td>
 * 		<td style="width:50%; border:#888 solid 1px;">
 * 			根据主键及其值，删除记录（特别地，当主键为数值类型）
 * 		</td>
 * 	</tr>
 * </table>
 * </p>
 * @author Yin Yong
 * @version 1.0
 */
package org.ixming.android.sqlite.provider;

import java.util.Map;

import org.ixming.android.sqlite.BaseSQLiteModel;

import android.content.Context;

