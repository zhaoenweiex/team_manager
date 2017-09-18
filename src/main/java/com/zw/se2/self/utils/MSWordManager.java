
package com.zw.se2.self.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

import java.util.List;

/**
 * MSWordManager
 * 
 * 
 * @version 1.0,2014-9-4
 * @author yinch
 * 
 */
public class MSWordManager {
	
	// word文档
	private Dispatch doc;

	// word运行程序对象
	private ActiveXComponent word;

	// 所有word文档集合
	private Dispatch documents;

	// 选定的范围或插入点
	private Dispatch selection;

	private boolean saveOnExit = true;

	/** */
	/**
	 * 
	 * @param visible
	 *            为true表示word应用程序可见
	 */
	public MSWordManager(boolean visible) {
		if (word == null) {
			word = new ActiveXComponent("Word.Application");
			word.setProperty("Visible", new Variant(false));
		}
		if (documents == null)
			documents = word.getProperty("Documents").toDispatch();
	}

	/** */
	/**
	 * 设置退出时参数
	 * 
	 * @param saveOnExit
	 *            boolean true-退出时保存文件，false-退出时不保存文件
	 */
	public void setSaveOnExit(boolean saveOnExit) {
		this.saveOnExit = saveOnExit;
	}

	/** */
	/**
	 * 创建一个新的word文档
	 * 
	 */
	public void createNewDocument() {
		doc = Dispatch.call(documents, "Add").toDispatch();
		selection = Dispatch.get(word, "Selection").toDispatch();
	}

	/** */
	/**
	 * 打开一个已存在的文档
	 * 
	 * @param docPath
	 */
	public void openDocument(String docPath) {
		closeDocument();
		doc = Dispatch.call(documents, "Open", docPath).toDispatch();
		selection = Dispatch.get(word, "Selection").toDispatch();
	}

	/** */
	/**
	 * 把选定的内容或插入点向上移动
	 * 
	 * @param pos
	 *            移动的距离
	 */
	public void moveUp(int pos) {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		for (int i = 0; i < pos; i++)
			Dispatch.call(selection, "MoveUp");

	}

	/** */
	/**
	 * 把选定的内容或者插入点向下移动
	 * 
	 * @param pos
	 *            移动的距离
	 */
	public void moveDown(int pos) {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		for (int i = 0; i < pos; i++)
			Dispatch.call(selection, "MoveDown");
	}

	/** */
	/**
	 * 把选定的内容或者插入点向左移动
	 * 
	 * @param pos
	 *            移动的距离
	 */
	public void moveLeft(int pos) {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		for (int i = 0; i < pos; i++) {
			Dispatch.call(selection, "MoveLeft");
		}
	}

	/** */
	/**
	 * 把选定的内容或者插入点向右移动
	 * 
	 * @param pos
	 *            移动的距离
	 */
	public void moveRight(int pos) {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		for (int i = 0; i < pos; i++)
			Dispatch.call(selection, "MoveRight");
	}

	/** */
	/**
	 * 把插入点移动到文件首位置
	 * 
	 */
	public void moveStart() {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		Dispatch.call(selection, "HomeKey", new Variant(6));
	}

	public void moveEnd() {
		if (selection == null)
			selection = Dispatch.get(word, "Selection").toDispatch();
		Dispatch.call(selection, "EndKey", new Variant(6));
	}

	/** */
	/**
	 * 从选定内容或插入点开始查找文本
	 * 
	 * @param toFindText
	 *            要查找的文本
	 * @return boolean true-查找到并选中该文本，false-未查找到文本
	 */
	@SuppressWarnings("static-access")
	public boolean find(String toFindText) {
		if (toFindText == null || toFindText.equals(""))
			return false;
		// 从selection所在位置开始查询
		Dispatch find = word.call(selection, "Find").toDispatch();
		// 设置要查找的内容
		Dispatch.put(find, "Text", toFindText);
		// 向前查找
		Dispatch.put(find, "Forward", "True");
		// 设置格式
		Dispatch.put(find, "Format", "True");
		// 大小写匹配
		Dispatch.put(find, "MatchCase", "True");
		// 全字匹配
		Dispatch.put(find, "MatchWholeWord", "True");
		// 查找并选中
		return Dispatch.call(find, "Execute").getBoolean();
	}

	/** */
	/**
	 * 把选定选定内容设定为替换文本
	 * 
	 * @param toFindText
	 *            查找字符串
	 * @param newText
	 *            要替换的内容
	 * @return
	 */
	public boolean replaceText(String toFindText, String newText) {
		if (!find(toFindText))
			return false;
		Dispatch.put(selection, "Text", newText);
		return true;
	}

	/** */
	/**
	 * 全局替换文本
	 * 
	 * @param toFindText
	 *            查找字符串
	 * @param newText
	 *            要替换的内容
	 */
	public void replaceAllText(String toFindText, String newText) {
//		moveStart();
		while (find(toFindText)) {
			Dispatch.put(selection, "Text", newText);
			Dispatch.call(selection, "MoveRight");
		}
	}

	/** */
	/**
	 * 在当前插入点插入字符串
	 * 
	 * @param newText
	 *            要插入的新字符串
	 */
	public void insertText(String newText) {
		Dispatch.put(selection, "Text", newText);
	}

	/** */
	/**
	 * 
	 * @param toFindText
	 *            要查找的字符串
	 * @param imagePath
	 *            图片路径
	 * @return
	 */
	public boolean replaceImage(String toFindText, String imagePath) {
		if (!find(toFindText))
			return false;
		Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddPicture", imagePath);
		return true;
	}

	/** */
	/**
	 * 全局替换图片
	 * 
	 * @param toFindText
	 *            查找字符串
	 * @param imagePath
	 *            图片路径
	 */
	public void replaceAllImage(String toFindText, String imagePath) {
		while (find(toFindText)) {
			Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
					"AddPicture", imagePath);
			Dispatch.call(selection, "MoveRight");
		}
	}

	/** */
	/**
	 * 在当前插入点插入图片
	 * 
	 * @param imagePath
	 *            图片路径
	 */
	public void insertImage(String imagePath, String text) {
		@SuppressWarnings("unused")
		Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddPicture", imagePath).toDispatch();
		//Dispatch.call(picture, "Select");
		if(text != null && !text.isEmpty()) {
			Dispatch.call(selection, "InsertCaption",
					new Variant("图表"), new Variant(" " + text), null,
					new Variant(1), new Variant(false));	
		}
		
	}
	
	/** */
	/**
	 * 在当前插入点一行内插入多张图片
	 * 
	 * @param imagePath
	 *            图片路径
	 */
	public void insertImages(String[] imagePaths, Integer totalWidth) {
		if(imagePaths == null || imagePaths.length < 1) {
			return;
		}
		if(totalWidth == null || totalWidth <= 0) {
			totalWidth = 400; //对应A4纸张，总宽度与约为400磅
		}
		//计算图片大小
		int width =0 ;
		int height = 0;
		if(imagePaths.length == 1) {
			width = 240;
		}
		else {
			width = totalWidth / imagePaths.length;
		}
		height = width * 3 / 4;
		
		for(int i=0; i<imagePaths.length; i++) {
			Dispatch picture = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
					"AddPicture", imagePaths[i]).toDispatch();
			//Dispatch.call(picture, "Select");
			Dispatch.put(picture, "Width", new Variant(width));
			Dispatch.put(picture, "Height", new Variant(height));
			this.MoveToSelectionEnd();
			//this.insertText(" "); //插入一个空格
			this.MoveToSelectionEnd();
		}
	}
	
	/**
	 * 在当前插入点插入一个柱状图
	 * 行坐标（每个分类）为rowTexts，列坐标（每个柱子）为columnTexts，数据为二维数组
	 * insertColumnChart
	 * 
	 * @param rowTexts
	 * @param columnTexts
	 * @param datas
	 */
	public void insertColumnChart(String[] rowTexts, String[] columnTexts, String[][] datas) {
		if(rowTexts == null || rowTexts.length < 1) {
			return;
		}
		if(columnTexts == null || columnTexts.length < 1) {
			return;
		}
		if(datas == null || datas.length < 1 || datas[0] == null || datas[0].length < 1){
			return;
		}
		if(rowTexts.length != datas.length || columnTexts.length != datas[0].length) {
			return;
		}
		int rows = rowTexts.length;
		int columns = columnTexts.length;
		
		//插入图表
		Dispatch inlineShape = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddChart", new Variant(51)).toDispatch(); //51,柱状图xlColumnClustered
		//Dispatch.put(inlineShape, "Width", new Variant(240));
		//Dispatch.put(inlineShape, "Height", new Variant(180));
		
		//操作数据表
		Dispatch chart = Dispatch.get(inlineShape, "Chart").toDispatch();
		Dispatch workbook = Dispatch.get(Dispatch.get(chart, "chartData").toDispatch(), "WorkBook").toDispatch();
		//立马隐藏
		Dispatch.put(Dispatch.get(workbook, "Application").toDispatch(), "Visible", new Variant(false));
		word.setProperty("Visible", new Variant(false));
		
		Dispatch sheets = Dispatch.call(workbook, "WorkSheets").toDispatch();
		Dispatch sheet = Dispatch.call(sheets, "Item", new Variant(1)).toDispatch();
		//设置行列数
		Dispatch.call(Dispatch.get(sheet, "Cells").toDispatch(), "Clear");
		String end = Dispatch.call(Dispatch.call(sheet, "Cells", new Variant(rows+1), new Variant(columns+1)).toDispatch(), 
				"Address").getString();
		String sourceData = "'Sheet1'!" + "$A$1:" + end;
		Dispatch.call(chart, "SetSourceData", new Variant(sourceData));
		
		//修改数据
		for(int i=0; i<rows; i++) {
			Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(1)).toDispatch(), "Value", new Variant(rowTexts[i]));	
		}
		for(int i=0; i<columns; i++) {
			Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(1), new Variant(i+2)).toDispatch(), "Value", new Variant(columnTexts[i]));	
		}
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(j+2)).toDispatch(), "Value", new Variant(datas[i][j]));	
			}
		}		
		
		//显示标签
		Dispatch.call(chart, "ApplyDataLabels", new Variant(2)); //2, 数据点的默认值（如果未指定此参数）
		
		//关闭workbook
		Dispatch.call(chart, "Refresh");
		Dispatch.call(workbook, "Close");
	}
	
	/**
	 * 利用OLE 
	 * 在当前插入点插入一个柱状图
	 * 行坐标（每个分类）为rowTexts，列坐标（每个柱子）为columnTexts，数据为二维数组
	 * insertColumnChartByOLE
	 *
	 * @param rowTexts
	 * @param columnTexts
	 * @param datas
	 */
	public void insertColumnChartByOLE(String[] rowTexts, String[] columnTexts, String[][] datas) {
		if(rowTexts == null || rowTexts.length < 1) {
			return;
		}
		if(columnTexts == null || columnTexts.length < 1) {
			return;
		}
		if(datas == null || datas.length < 1 || datas[0] == null || datas[0].length < 1){
			return;
		}
		if(rowTexts.length != datas.length || columnTexts.length != datas[0].length) {
			return;
		}
		int rows = rowTexts.length;
		int columns = columnTexts.length;
		
		//插入图表
		Dispatch inlineShape = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddOLEObject", new Variant("Excel.Chart")).toDispatch();		
		Dispatch.put(inlineShape, "Width", new Variant(320));
		Dispatch.put(inlineShape, "Height", new Variant(240));
		
		//操作数据表
		Dispatch workbook = Dispatch.get(Dispatch.get(inlineShape, "OLEFormat").toDispatch(), "Object").toDispatch();
		Dispatch sheets = Dispatch.call(workbook, "Sheets").toDispatch();
		Dispatch chart = Dispatch.call(sheets, "Item", new Variant(1)).toDispatch();
		Dispatch.put(chart, "ChartType", new Variant(51)); //51,柱状图xlColumnClustered
		//Dispatch.put(Dispatch.get(chart, "ChartArea").toDispatch(), "Width", new Variant(320));
		//Dispatch.put(Dispatch.get(chart, "ChartArea").toDispatch(), "Height", new Variant(240));
		Dispatch sheet = Dispatch.call(sheets, "Item", new Variant(2)).toDispatch();
		
		//设置行列数
		Dispatch.call(Dispatch.get(sheet, "Cells").toDispatch(), "Clear");
		String end = Dispatch.call(Dispatch.call(sheet, "Cells", new Variant(rows+1), new Variant(columns+1)).toDispatch(), 
				"Address").getString();
		Dispatch.call(chart, "SetSourceData", Dispatch.call(sheet, "Range", new Variant("$A$1:" + end)).toDispatch());
		
		//修改数据
		for(int i=0; i<rows; i++) {
			Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(1)).toDispatch(), "Value", new Variant(rowTexts[i]));	
		}
		for(int i=0; i<columns; i++) {
			Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(1), new Variant(i+2)).toDispatch(), "Value", new Variant(columnTexts[i]));	
		}
		for(int i=0; i<rows; i++) {
			for(int j=0; j<columns; j++) {
				Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(j+2)).toDispatch(), "Value", new Variant(datas[i][j]));	
			}
		}		
		
		//显示标签
		Dispatch.call(chart, "ApplyDataLabels", new Variant(2)); //2, 数据点的默认值（如果未指定此参数）
	}
	
	
	/**
	 * 在当前插入点插入一个饼图
	 * 行坐标（每个分类）为rowTexts，列坐标（标题）为columnText，数据为一维数组
	 * insertPieChart
	 *
	 * @param rowTexts
	 * @param columnText
	 * @param datas
	 */
	public void insertPieChart(String[] rowTexts, String columnText, String[] datas) {
		if(rowTexts == null || rowTexts.length < 1) {
			return;
		}
		if(datas == null || datas.length < 1){
			return;
		}
		if(rowTexts.length != datas.length) {
			return;
		}
		int rows = rowTexts.length;
		
		//插入图表
		Dispatch inlineShape = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddChart", new Variant(5)).toDispatch(); //5,饼图xlPie
		//Dispatch.put(inlineShape, "Width", new Variant(240));
		//Dispatch.put(inlineShape, "Height", new Variant(180));
		
		//操作数据表
		Dispatch chart = Dispatch.get(inlineShape, "Chart").toDispatch();
		Dispatch workbook = Dispatch.get(Dispatch.get(chart, "chartData").toDispatch(), "WorkBook").toDispatch();
		//立马隐藏
		Dispatch.put(Dispatch.get(workbook, "Application").toDispatch(), "Visible", new Variant(false));
		word.setProperty("Visible", new Variant(false));
		
		Dispatch sheets = Dispatch.call(workbook, "WorkSheets").toDispatch();
		Dispatch sheet = Dispatch.call(sheets, "Item", new Variant(1)).toDispatch();
		//设置行列数
		Dispatch.call(Dispatch.get(sheet, "Cells").toDispatch(), "Clear");
		String end = Dispatch.call(Dispatch.call(sheet, "Cells", new Variant(rows+1), new Variant(2)).toDispatch(), 
				"Address").getString();
		String sourceData = "'Sheet1'!" + "$A$1:" + end;
		Dispatch.call(chart, "SetSourceData", new Variant(sourceData));
		
		//修改数据
		for(int i=0; i<rows; i++) {
			Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(1)).toDispatch(), "Value", new Variant(rowTexts[i]));	
		}
		Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(1), new Variant(2)).toDispatch(), "Value", new Variant(columnText));	
		for(int i=0; i<rows; i++) {
			Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(2)).toDispatch(), "Value", new Variant(datas[i]));	
		}		
		
		//显示标签
		Dispatch.call(chart, "ApplyDataLabels", new Variant(2)); //2, 数据点的默认值（如果未指定此参数）
		//Dispatch.call(chart, "ApplyDataLabels", new Variant(3)); //3, 占总数的百分比。仅用于饼图和圆环图
		
		//关闭workbook
		Dispatch.call(chart, "Refresh");
		Dispatch.call(workbook, "Close");
	}
	
	/**
	 * 在当前插入点插入一个饼图（显示百分比，保留两位小数，若为0不显示）
	 * 行坐标（每个分类）为rowTexts，列坐标（标题）为columnText，数据为一维数组
	 * Excel中自动计算百分比
	 * 
	 * insertPieChartPercent
	 *
	 * @param rowTexts
	 * @param columnText
	 * @param datas
	 */
	public void insertPieChartPercent(String[] rowTexts, String columnText, String[] datas) {
		if(rowTexts == null || rowTexts.length < 1) {
			return;
		}
		if(datas == null || datas.length < 1){
			return;
		}
		if(rowTexts.length != datas.length) {
			return;
		}
		int rows = rowTexts.length;
		
		//插入图表
		Dispatch inlineShape = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddChart", new Variant(5)).toDispatch(); //5,饼图xlPie
		//Dispatch.put(inlineShape, "Width", new Variant(240));
		//Dispatch.put(inlineShape, "Height", new Variant(180));
		
		//操作数据表
		Dispatch chart = Dispatch.get(inlineShape, "Chart").toDispatch();
		Dispatch workbook = Dispatch.get(Dispatch.get(chart, "chartData").toDispatch(), "WorkBook").toDispatch();
		//立马隐藏
		//Dispatch.put(Dispatch.get(workbook, "Application").toDispatch(), "Visible", new Variant(false));
		//word.setProperty("Visible", new Variant(false));
		
		Dispatch sheets = Dispatch.call(workbook, "WorkSheets").toDispatch();
		Dispatch sheet = Dispatch.call(sheets, "Item", new Variant(1)).toDispatch();
		//设置行列数
		Dispatch.call(Dispatch.get(sheet, "Cells").toDispatch(), "Clear");
		String end = Dispatch.call(Dispatch.call(sheet, "Cells", new Variant(rows+1), new Variant(2)).toDispatch(), 
				"Address").getString();
		String sourceData = "'Sheet1'!" + "$A$1:" + end;
		Dispatch.call(chart, "SetSourceData", new Variant(sourceData));
		
		//修改数据
		for(int i=0; i<rows; i++) {
			Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(1)).toDispatch(), "Value", new Variant(rowTexts[i]));	
		}
		Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(1), new Variant(2)).toDispatch(), "Value", new Variant(columnText));
		//将数据放在C列
		for(int i=0; i<rows; i++) {
			Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(3)).toDispatch(), "Value", new Variant(datas[i]));	
		}
		//将数据之和放在rows+2行
		Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(rows+2), new Variant(3)).toDispatch(), "Formula", new Variant("=SUM($C$2:$C$" + (rows+1) +")"));
		//判读分母是否是0！！！
		Double total = Dispatch.get(Dispatch.call(sheet, "Cells", new Variant(rows+2), new Variant(3)).toDispatch(), "Value").getDouble();
		if(total !=null && !total.equals(new Double(0))) {
			//将百分比放在B列
			Dispatch.put(Dispatch.call(sheet, "Columns", new Variant(2)).toDispatch(), "NumberFormat", new Variant("0.00%"));
			for(int i=0; i<rows; i++) {	
				Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(2)).toDispatch(), "Formula", new Variant("=$C$" + (i+2) + "/$C$" + (rows+2)));
				Double value = Dispatch.get(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(2)).toDispatch(), "Value").getDouble();
				if(value == null || value.equals(new Double(0))) {
					Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(2)).toDispatch(), "Value", new Variant(""));
				}
			}
		}

		//显示标签
		Dispatch.call(chart, "ApplyDataLabels", new Variant(2)); //2, 数据点的默认值（如果未指定此参数）
		
		//关闭workbook
		Dispatch.call(chart, "Refresh");
		Dispatch.call(workbook, "Close");
	}
	
	
	/**
	 * 有bug！！！使用OLE，在当前插入点插入一个饼图
	 * 貌似又没有bug
	 * 行坐标（每个分类）为rowTexts，列坐标（标题）为columnText，数据为一维数组
	 * insertPieChartByOLE
	 *
	 * @param rowTexts
	 * @param columnText
	 * @param datas
	 */
	public void insertPieChartByOLE(String[] rowTexts, String columnText, String[] datas) {
		if(rowTexts == null || rowTexts.length < 1) {
			return;
		}
		if(datas == null || datas.length < 1){
			return;
		}
		if(rowTexts.length != datas.length) {
			return;
		}
		int rows = rowTexts.length;
		
		//插入图表
		Dispatch inlineShape = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddOLEObject", new Variant("Excel.Chart")).toDispatch();		
		Dispatch.put(inlineShape, "Width", new Variant(320));
		Dispatch.put(inlineShape, "Height", new Variant(240));
		
		//操作数据表
		Dispatch workbook = Dispatch.get(Dispatch.get(inlineShape, "OLEFormat").toDispatch(), "Object").toDispatch();
		Dispatch sheets = Dispatch.call(workbook, "Sheets").toDispatch();
		Dispatch chart = Dispatch.call(sheets, "Item", new Variant(1)).toDispatch();
		Dispatch.put(chart, "ChartType", new Variant(5)); //5,饼图xlPie
		Dispatch.put(Dispatch.get(chart, "ChartArea").toDispatch(), "Width", new Variant(320));
		Dispatch.put(Dispatch.get(chart, "ChartArea").toDispatch(), "Height", new Variant(240));
		Dispatch sheet = Dispatch.call(sheets, "Item", new Variant(2)).toDispatch();
		//设置行列数
		String end = Dispatch.call(Dispatch.call(sheet, "Cells", new Variant(rows+1), new Variant(2)).toDispatch(), 
				"Address").getString();
		Dispatch.call(Dispatch.get(sheet, "Cells").toDispatch(), "Clear");
		Dispatch.call(chart, "SetSourceData", Dispatch.call(sheet, "Range", new Variant("$A$1:" + end)).toDispatch());
		
		//修改数据
		for(int i=0; i<rows; i++) {
			Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(1)).toDispatch(), "Value", new Variant(rowTexts[i]));	
		}
		Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(1), new Variant(2)).toDispatch(), "Value", new Variant(columnText));	
		for(int i=0; i<rows; i++) {
			Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(2)).toDispatch(), "Value", new Variant(datas[i]));	
		}		
		
		//显示标签
		Dispatch.call(chart, "ApplyDataLabels", new Variant(2)); //2, 数据点的默认值（如果未指定此参数）
		//Dispatch.call(chart, "ApplyDataLabels", new Variant(3)); //3, 占总数的百分比。仅用于饼图和圆环图
	}
	
	
	/**
	 * 使用OLE
	 * 在当前插入点插入一个饼图（显示百分比，保留两位小数，若为0不显示）
	 * 行坐标（每个分类）为rowTexts，列坐标（标题）为columnText，数据为一维数组
	 * Excel中自动计算百分比
	 * 
	 * insertPieChartPercentByOLE
	 *
	 * @param rowTexts
	 * @param columnText
	 * @param datas
	 */
	public void insertPieChartPercentByOLE(String[] rowTexts, String columnText, String[] datas) {
		if(rowTexts == null || rowTexts.length < 1) {
			return;
		}
		if(datas == null || datas.length < 1){
			return;
		}
		if(rowTexts.length != datas.length) {
			return;
		}
		int rows = rowTexts.length;
		
		//插入图表
		Dispatch inlineShape = Dispatch.call(Dispatch.get(selection, "InLineShapes").toDispatch(),
				"AddOLEObject", new Variant("Excel.Chart")).toDispatch();		
		Dispatch.put(inlineShape, "Width", new Variant(320));
		Dispatch.put(inlineShape, "Height", new Variant(240));
		
		//操作数据表
		Dispatch workbook = Dispatch.get(Dispatch.get(inlineShape, "OLEFormat").toDispatch(), "Object").toDispatch();
		Dispatch sheets = Dispatch.call(workbook, "Sheets").toDispatch();
		Dispatch chart = Dispatch.call(sheets, "Item", new Variant(1)).toDispatch();
		Dispatch.put(chart, "ChartType", new Variant(5)); //5,饼图xlPie
		//Dispatch.put(Dispatch.get(chart, "ChartArea").toDispatch(), "Width", new Variant(320));
		//Dispatch.put(Dispatch.get(chart, "ChartArea").toDispatch(), "Height", new Variant(240));
		Dispatch sheet = Dispatch.call(sheets, "Item", new Variant(2)).toDispatch();
		//设置行列数
		String end = Dispatch.call(Dispatch.call(sheet, "Cells", new Variant(rows+1), new Variant(2)).toDispatch(), 
				"Address").getString();
		Dispatch.call(Dispatch.get(sheet, "Cells").toDispatch(), "Clear");
		Dispatch.call(chart, "SetSourceData", Dispatch.call(sheet, "Range", new Variant("$A$1:" + end)).toDispatch());
		
		//修改数据
		for(int i=0; i<rows; i++) {
			Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(1)).toDispatch(), "Value", new Variant(rowTexts[i]));	
		}
		Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(1), new Variant(2)).toDispatch(), "Value", new Variant(columnText));
		//将数据放在C列
		for(int i=0; i<rows; i++) {
			Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(3)).toDispatch(), "Value", new Variant(datas[i]));	
		}
		//将数据之和放在rows+2行
		Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(rows+2), new Variant(3)).toDispatch(), "Formula", new Variant("=SUM($C$2:$C$" + (rows+1) +")"));
		//判读分母是否是0！！！
		Double total = Dispatch.get(Dispatch.call(sheet, "Cells", new Variant(rows+2), new Variant(3)).toDispatch(), "Value").getDouble();
		if(total !=null && !total.equals(new Double(0))) {
			//将百分比放在B列
			Dispatch.put(Dispatch.call(sheet, "Columns", new Variant(2)).toDispatch(), "NumberFormat", new Variant("0.00%"));
			for(int i=0; i<rows; i++) {	
				Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(2)).toDispatch(), "Formula", new Variant("=$C$" + (i+2) + "/$C$" + (rows+2)));
				Double value = Dispatch.get(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(2)).toDispatch(), "Value").getDouble();
				if(value == null || value.equals(new Double(0))) {
					Dispatch.put(Dispatch.call(sheet, "Cells", new Variant(i+2), new Variant(2)).toDispatch(), "Value", new Variant(""));
				}
			}
		}
		
		
		//显示标签
		Dispatch.call(chart, "ApplyDataLabels", new Variant(2)); //2, 数据点的默认值（如果未指定此参数）
		//Dispatch.call(chart, "ApplyDataLabels", new Variant(3)); //3, 占总数的百分比。仅用于饼图和圆环图
		
		//关闭workbook
//		Dispatch.call(chart, "Refresh");
//		Dispatch.call(workbook, "Close",new Variant(true));

	}
	

	/** */
	/**
	 * 在当前文档拷贝数据
	 * 
	 * @param pos
	 */
	public void copy(String toCopyText) {
		moveStart();
		if (this.find(toCopyText)) {
			Dispatch textRange = Dispatch.get(selection, "Range").toDispatch();
			Dispatch.call(textRange, "Copy");
		}
	}

	/** */
	/**
	 * 在当前文档粘帖剪贴板数据
	 * 
	 * @param pos
	 */
	public void paste(String pos) {
		moveStart();
		if (this.find(pos)) {
			Dispatch textRange = Dispatch.get(selection, "Range").toDispatch();
			Dispatch.call(textRange, "Paste");
		}
	}

	/** */
	/**
	 * 在当前文档指定的位置拷贝表格
	 * 
	 * @param pos
	 *            当前文档指定的位置
	 * @param tableIndex
	 *            被拷贝的表格在word文档中所处的位置
	 */
	public void copyTable(String pos, int tableIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch range = Dispatch.get(table, "Range").toDispatch();
		Dispatch.call(range, "Copy");
		if (this.find(pos)) {
			Dispatch textRange = Dispatch.get(selection, "Range").toDispatch();
			Dispatch.call(textRange, "Paste");
		}
	}

	/** */
	/**
	 * 在当前文档末尾拷贝来自另一个文档中的段落
	 * 
	 * @param anotherDocPath
	 *            另一个文档的磁盘路径
	 * @param tableIndex
	 *            被拷贝的段落在另一格文档中的序号(从1开始)
	 */
	public void copyParagraphFromAnotherDoc(String anotherDocPath,
			int paragraphIndex) {
		Dispatch wordContent = Dispatch.get(doc, "Content").toDispatch(); // 取得当前文档的内容
		Dispatch.call(wordContent, "InsertAfter", "$selection$");// 插入特殊符定位插入点
		copyParagraphFromAnotherDoc(anotherDocPath, paragraphIndex,
				"$selection$");
	}

	/** */
	/**
	 * 在当前文档指定的位置拷贝来自另一个文档中的段落
	 * 
	 * @param anotherDocPath
	 *            另一个文档的磁盘路径
	 * @param tableIndex
	 *            被拷贝的段落在另一格文档中的序号(从1开始)
	 * @param pos
	 *            当前文档指定的位置
	 */
	public void copyParagraphFromAnotherDoc(String anotherDocPath,
			int paragraphIndex, String pos) {
		Dispatch doc2 = null;
		try {
			doc2 = Dispatch.call(documents, "Open", anotherDocPath)
					.toDispatch();
			Dispatch paragraphs = Dispatch.get(doc2, "Paragraphs").toDispatch();

			Dispatch paragraph = Dispatch.call(paragraphs, "Item",
					new Variant(paragraphIndex)).toDispatch();
			Dispatch range = Dispatch.get(paragraph, "Range").toDispatch();
			Dispatch.call(range, "Copy");
			if (this.find(pos)) {
				Dispatch textRange = Dispatch.get(selection, "Range")
						.toDispatch();
				Dispatch.call(textRange, "Paste");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (doc2 != null) {
				Dispatch.call(doc2, "Close", new Variant(saveOnExit));
				doc2 = null;
			}
		}
	}

	/** */
	/**
	 * 在当前文档指定的位置拷贝来自另一个文档中的表格
	 * 
	 * @param anotherDocPath
	 *            另一个文档的磁盘路径
	 * @param tableIndex
	 *            被拷贝的表格在另一格文档中的序号(从1开始)
	 * @param pos
	 *            当前文档指定的位置
	 */
	public void copyTableFromAnotherDoc(String anotherDocPath, int tableIndex,
			String pos) {
		Dispatch doc2 = null;
		try {
			doc2 = Dispatch.call(documents, "Open", anotherDocPath)
					.toDispatch();
			Dispatch tables = Dispatch.get(doc2, "Tables").toDispatch();
			Dispatch table = Dispatch.call(tables, "Item",
					new Variant(tableIndex)).toDispatch();
			Dispatch range = Dispatch.get(table, "Range").toDispatch();
			Dispatch.call(range, "Copy");
			if (this.find(pos)) {
				Dispatch textRange = Dispatch.get(selection, "Range")
						.toDispatch();
				Dispatch.call(textRange, "Paste");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (doc2 != null) {
				Dispatch.call(doc2, "Close", new Variant(saveOnExit));
				doc2 = null;
			}
		}
	}

	/** */
	/**
	 * 在当前文档指定的位置拷贝来自另一个文档中的图片
	 * 
	 * @param anotherDocPath
	 *            另一个文档的磁盘路径
	 * @param shapeIndex
	 *            被拷贝的图片在另一格文档中的位置
	 * @param pos
	 *            当前文档指定的位置
	 */
	public void copyImageFromAnotherDoc(String anotherDocPath, int shapeIndex,
			String pos) {
		Dispatch doc2 = null;
		try {
			doc2 = Dispatch.call(documents, "Open", anotherDocPath)
					.toDispatch();
			Dispatch shapes = Dispatch.get(doc2, "InLineShapes").toDispatch();
			Dispatch shape = Dispatch.call(shapes, "Item",
					new Variant(shapeIndex)).toDispatch();
			Dispatch imageRange = Dispatch.get(shape, "Range").toDispatch();
			Dispatch.call(imageRange, "Copy");
			if (this.find(pos)) {
				Dispatch textRange = Dispatch.get(selection, "Range")
						.toDispatch();
				Dispatch.call(textRange, "Paste");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (doc2 != null) {
				Dispatch.call(doc2, "Close", new Variant(saveOnExit));
				doc2 = null;
			}
		}
	}

	/** */
	/**
	 * 创建表格
	 * 
	 * @param pos
	 *            位置
	 * @param cols
	 *            列数
	 * @param rows
	 *            行数
	 */
	public void createTable(int numCols, int numRows) {// (String pos, int
														// numCols, int numRows)
														// {
	// if (!find(pos)) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		Dispatch range = Dispatch.get(selection, "Range").toDispatch();
		@SuppressWarnings("unused")
		Dispatch newTable = Dispatch.call(tables, "Add", range, new Variant(numRows), new Variant(numCols), new Variant(1)).toDispatch();
		Dispatch.call(selection, "MoveRight");
		moveEnd();
		// }
	}

	/** */
	/**
	 * 在指定行前面增加行
	 * 
	 * @param tableIndex
	 *            word文件中的第N张表(从1开始)
	 * @param rowIndex
	 *            指定行的序号(从1开始)
	 */
	public void addTableRowBefore(int tableIndex, int rowIndex) {
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		Dispatch row = Dispatch.call(rows, "Item", new Variant(rowIndex))
				.toDispatch();
		Dispatch.call(rows, "Add", new Variant(row));
	}
	
	public void addTableRowAfterUseEnter(int tableIndex, int rowIndex) {
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		Dispatch row = Dispatch.call(rows, "Item", new Variant(rowIndex))
				.toDispatch();
		Dispatch range = Dispatch.get(row, "Range").toDispatch();
		Dispatch.call(range, "Select");
		moveRight(1);
		Dispatch.call(selection, "Enter");
	}
	/** */
	/**
	 * 在指定行后面增加行
	 * 
	 * @param tableIndex
	 *            word文件中的第N张表(从1开始)
	 * @param rowIndex
	 *            指定行的序号(从1开始)
	 */
	public void addTableRowAfter(int tableIndex, int rowIndex) {
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		Dispatch row = Dispatch.call(rows, "Item", new Variant(rowIndex+1))
				.toDispatch();
		Dispatch.call(rows, "Add", new Variant(row));
	}
	

	/** */
	/**
	 * 在第1行前增加一行
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addFirstTableRow(int tableIndex) {
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		Dispatch row = Dispatch.get(rows, "First").toDispatch();
		Dispatch.call(rows, "Add", new Variant(row));
	}

	/** */
	/**
	 * 在最后1行前增加一行
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addLastTableRow(int tableIndex) {
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		Dispatch row = Dispatch.get(rows, "Last").toDispatch();
		Dispatch.call(rows, "Add", new Variant(row));
	}

	/** */
	/**
	 * 增加一行
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addRow(int tableIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		Dispatch.call(rows, "Add");
	}

	/** */
	/**
	 * 增加一列
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addCol(int tableIndex) {
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
		Dispatch.call(cols, "Add").toDispatch();
		Dispatch.call(cols, "AutoFit");
	}

	/** */
	/**
	 * 在指定列前面增加表格的列
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 * @param colIndex
	 *            指定列的序号 (从1开始)
	 */
	public void addTableCol(int tableIndex, int colIndex) {
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
		System.out.println(Dispatch.get(cols, "Count"));
		Dispatch col = Dispatch.call(cols, "Item", new Variant(colIndex))
				.toDispatch();
		// Dispatch col = Dispatch.get(cols, "First").toDispatch();
		Dispatch.call(cols, "Add", col).toDispatch();
		Dispatch.call(cols, "AutoFit");
	}

	/** */
	/**
	 * 在第1列前增加一列
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addFirstTableCol(int tableIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
		Dispatch col = Dispatch.get(cols, "First").toDispatch();
		Dispatch.call(cols, "Add", col).toDispatch();
		Dispatch.call(cols, "AutoFit");
	}

	/** */
	/**
	 * 在最后一列前增加一列
	 * 
	 * @param tableIndex
	 *            word文档中的第N张表(从1开始)
	 */
	public void addLastTableCol(int tableIndex) {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 表格的所有行
		Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
		Dispatch col = Dispatch.get(cols, "Last").toDispatch();
		Dispatch.call(cols, "Add", col).toDispatch();
		Dispatch.call(cols, "AutoFit");
	}

	/** */
	/**
	 * 合并单元格
	 * 
	 * @param tableIndex
	 * @param fstCellRowIdx
	 * @param fstCellColIdx
	 * @param secCellRowIdx
	 * @param secCellColIdx
	 */
	public void mergeCell(int tableIndex, int fstCellRowIdx, int fstCellColIdx,
			int secCellRowIdx, int secCellColIdx) {
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch fstCell = Dispatch.call(table, "Cell",
				new Variant(fstCellRowIdx), new Variant(fstCellColIdx))
				.toDispatch();
		Dispatch secCell = Dispatch.call(table, "Cell",
				new Variant(secCellRowIdx), new Variant(secCellColIdx))
				.toDispatch();
		Dispatch.call(fstCell, "Merge", secCell);
	}

	/** */
	/**
	 * 在指定的单元格里填写数据
	 * 
	 * @param tableIndex
	 * @param cellRowIdx
	 * @param cellColIdx
	 * @param txt
	 */
	public void putTxtToCell(int tableIndex, int cellRowIdx, int cellColIdx,
			String txt) {
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		Dispatch cell = Dispatch.call(table, "Cell", new Variant(cellRowIdx),
				new Variant(cellColIdx)).toDispatch();
		Dispatch.call(cell, "Select");
		Dispatch.put(selection, "Text", txt);
	}
	
	/**
	 * 在指定的表格里填充一行数据（必须确保该行存在）
	 * 
	 * @param tableIndex
	 * @param rowIndex
	 * @param colIndex
	 * @param data
	 */
	@SuppressWarnings("unchecked")
	public void putTxtToRow(int tableIndex, int rowIndex, int colIndex, List data) {
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		// 填充数据
		for(int i=0; i<data.size(); i++) {
			Dispatch cell = Dispatch.call(table, "Cell", new Variant(rowIndex),
					new Variant(colIndex+i)).toDispatch();
			Dispatch.call(cell, "Select");
			Dispatch.put(selection, "Text", data.get(i));
		}
	}

	/**
	 * 在指定的表格里填充n行数据（必须确保第一行存在）
	 * 
	 * @param tableIndex
	 * @param rowIndex
	 * @param colIndex
	 * @param data
	 */
	@SuppressWarnings("unchecked")
	public void putTxtToRows(int tableIndex, int rowIndex, int colIndex, List<List> datas) {
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex)).toDispatch();
		// 表格的所有行
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		// 单元格
		Dispatch cellLT = Dispatch.call(table, "Cell", new Variant(rowIndex), new Variant(colIndex)).toDispatch();
		// 选中单元格所在行
		Dispatch.call(cellLT, "Select");
		Dispatch.call(selection, "SelectRow");
		// 插入n-1行
		for(int i=0; i<datas.size()-1; i++) {
			Dispatch.call(rows, "Add", selection);
		}
		// 填充数据
		for(int i=0; i<datas.size(); i++) {
			List data = datas.get(i);
			for(int j=0; j<data.size(); j++) {
				Dispatch cell = Dispatch.call(table, "Cell", new Variant(rowIndex+i),
						new Variant(colIndex+j)).toDispatch();
				Dispatch.call(cell, "Select");
				Dispatch.put(selection, "Text", String.valueOf(data.get(j)));				
			}
		}
	}
	
	/**
	 * 在指定的表格里填充最后一行数据（必须确保该行存在）
	 * 
	 * @param tableIndex
	 * @param colIndex
	 * @param data
	 */
	@SuppressWarnings("unchecked")
	public void putTxtToLastRow(int tableIndex, int colIndex, List data) {
		// 所有表格
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		// 要填充的表格
		Dispatch table = Dispatch.call(tables, "Item", new Variant(tableIndex))
				.toDispatch();
		//行数
		Dispatch rows = Dispatch.get(table, "Rows").toDispatch();
		int rowsCount = Dispatch.get(rows, "Count").getInt();
		// 填充数据
		for(int i=0; i<data.size(); i++) {
			Dispatch cell = Dispatch.call(table, "Cell", new Variant(rowsCount),
					new Variant(colIndex+i)).toDispatch();
			Dispatch.call(cell, "Select");
			Dispatch.put(selection, "Text", String.valueOf(data.get(i)));
		}
	}
	
	/** */
	/**
	 * 自动调整表格
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void autoFitTable() {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
		int count = Dispatch.get(tables, "Count").toInt();
		for (int i = 0; i < count; i++) {
			Dispatch table = Dispatch.call(tables, "Item", new Variant(i + 1))
					.toDispatch();
			Dispatch cols = Dispatch.get(table, "Columns").toDispatch();
			Dispatch.call(cols, "AutoFit");
		}
	}

	/** */
	/**
	 * 调用word里的宏以调整表格的宽度,其中宏保存在document下
	 * 
	 */
	public void callWordMacro() {
		Dispatch tables = Dispatch.get(doc, "Tables").toDispatch();
//		int count = Dispatch.get(tables, "Count").toInt();
		Variant vMacroName = new Variant("Normal.NewMacros.tableFit");
//		Variant vParam = new Variant("param1");
		Variant para[] = new Variant[] { vMacroName };
		for (int i = 0; i < para.length; i++) {
			Dispatch table = Dispatch.call(tables, "Item", new Variant(i + 1))
					.toDispatch();
			Dispatch.call(table, "Select");
			Dispatch.call(word, "Run", "tableFitContent");
		}
	}

	/** */
	/**
	 * 设置当前选定内容的字体
	 * 
	 * @param boldSize
	 * @param italicSize
	 * @param underLineSize
	 *            下划线
	 * @param colorSize
	 *            字体颜色
	 * @param size
	 *            字体大小
	 * @param name
	 *            字体名称
	 */
	public void setFont(boolean bold, boolean italic, boolean underLine, String colorSize, String size, String name) {
		Dispatch font = Dispatch.get(selection, "Font").toDispatch();
		Dispatch.put(font, "Name", new Variant(name));
		Dispatch.put(font, "Bold", new Variant(bold));
		Dispatch.put(font, "Italic", new Variant(italic));
		Dispatch.put(font, "Underline", new Variant(underLine));
		Dispatch.put(font, "Color", colorSize);
		Dispatch.put(font, "Size", size);
	}
	
	/**
	 * 
	 * 设置段落
	 * 
	 * @param align
	 *            默认:居左, 0:居左, 1:居中, 2:居右, 3:两端对齐, 4:分散对齐
	 * 
	 */
	public void setParagraph(int align) {
		if (align <= 0 || align > 4) {
			align = 0;
		}
		Dispatch alignment = Dispatch.get(selection, "ParagraphFormat")
				.toDispatch();
		Dispatch.put(alignment, "Alignment", align);
	}

	/**
	 * 设置样式
	 * @param styleName 样式名称
	 */
	public void setStyle(String styleName) {
		if(styleName == null || styleName.isEmpty()) {
			return;
		}
		Dispatch range = Dispatch.get(selection, "Range").toDispatch();
		Dispatch.put(range, "style", styleName);
	}
	
	/**
	 * 插入页眉
	 * setHeaderText
	 *
	 * @param headerText
	 */
	public void setHeaderText(String headerText, int sectionIndex) {
		Dispatch sections = Dispatch.get(doc, "Sections").toDispatch();
		Dispatch section = Dispatch.call(sections, "Item", new Variant(sectionIndex)).toDispatch();
		Dispatch headers = Dispatch.get(section, "Headers").toDispatch();
		Dispatch header = Dispatch.call(headers, "Item", new Variant(1)).toDispatch();
		Dispatch range = Dispatch.get(header, "Range").toDispatch();
		Dispatch.put(range, "Text", headerText);
	}
	
	/**
	 * 切换视图，若hearderFooter为true，切换至页眉页脚视图，否则为正文
	 * changeView
	 *
	 * @param hearderFooter
	 */
	public void changeView(boolean hearderFooter) {
		Dispatch activeWindow = Dispatch.get(word, "ActiveWindow").toDispatch();
		Dispatch view = Dispatch.get(activeWindow, "View").toDispatch();
		if(hearderFooter) {
			Dispatch.put(view, "SeekView", new Variant(9));	
		}
		else {
			Dispatch.put(view, "SeekView", new Variant(0));	
		}
	}
	
	/**
	 * 移动到书签并选中
	 * @param bookMarkKey
	 * @return
	 */
	public boolean MoveToBookMark(String bookMarkKey) {
		Dispatch bookMarks = Dispatch.call(doc, "BookMarks").toDispatch();
		boolean bookMarkExist = Dispatch.call(bookMarks, "Exists", bookMarkKey).getBoolean();
		if(bookMarkExist) {
			Dispatch rangeItem = Dispatch.call(bookMarks, "Item", bookMarkKey).toDispatch();
			Dispatch range = Dispatch.call(rangeItem, "Range").toDispatch();
			Dispatch.call(range, "Select");
			return true;
		}
		return false;
	}
	
	/**
	 * 移动光标到选中位置的后面
	 */
	public void MoveToSelectionEnd() {
		Dispatch.call(selection, "MoveRight");
	}
	
	/**
	 * 更新目录
	 * @param onlyPageNumbers
	 */
	public void updateTableOfContent(boolean onlyPageNumbers) {
		Dispatch tablesOfContents = Dispatch.call(doc, "TablesOfContents").toDispatch();
		Dispatch tablesOfContent = Dispatch.call(tablesOfContents, "Item", new Variant(1)).toDispatch();
		if(onlyPageNumbers) {
			Dispatch.call(tablesOfContent, "UpdatePageNumbers");
		}
		else {
			Dispatch.call(tablesOfContent, "Update");
		}
	}
	
	/** */
	/**
	 * 文件保存或另存为
	 * 
	 * @param savePath
	 *            保存或另存为路径
	 */
	public void save(String savePath) {
		Dispatch.call((Dispatch) Dispatch.call(word, "WordBasic").getDispatch(), "FileSaveAs", savePath);
	}

	/** */
	/**
	 * 关闭当前word文档
	 * 
	 */
	public void closeDocument() {
		if (doc != null) {
			Dispatch.call(doc, "Save");
			Dispatch.call(doc, "Close", new Variant(saveOnExit));
			doc = null;
		}
	}

	/** */
	/**
	 * 关闭全部应用
	 * 
	 */
	public void close() {
		closeDocument();
		if (word != null) {
			Dispatch.call(word, "Quit");
			word = null;
		}
		selection = null;
		documents = null;
	}

	/** */
	/**
	 * 打印当前word文档
	 * 
	 */
	public void printFile() {
		if (doc != null) {
			Dispatch.call(doc, "PrintOut");
		}
	}


}


