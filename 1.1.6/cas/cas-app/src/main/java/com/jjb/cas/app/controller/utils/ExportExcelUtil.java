package com.jjb.cas.app.controller.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import com.jjb.unicorn.facility.util.DateUtils;




/**
 * 
 * <P>导出Execl工具类</P>
 */
public class ExportExcelUtil {

	private static String NUMBER_FORMAT = "#,##0.00";

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat(DateUtils.FULL_SECOND_LINE);

	private OutputStream out = null;
	private Workbook workbook = null;
	private Sheet sheet = null;
	private Row row = null;

	public ExportExcelUtil() {
	}

	public ExportExcelUtil(final OutputStream out) {
		this.out = out;
//		 in POI 3.8-beta3.The default window size is 100 and defined by
//		 SXSSFWorkbook.DEFAULT_WINDOW_SIZE.
//		 keep 100 rows in memory,exceeding rows will be flushed to disk
		this.workbook = new SXSSFWorkbook(128);
		this.sheet = workbook.createSheet();
	}

	/**
	 * <p>导出Excel文件</p>
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author BIG.CPU
	 */
	public void exportExcel() throws FileNotFoundException, IOException {
		try {
			workbook.write(out);
			out.flush();
			out.close();
		} catch (final FileNotFoundException e) {
			throw new IOException(" 生成导出Excel文件出错! ", e);
		} catch (final IOException e) {
			throw new IOException(" 写入Excel文件出错! ", e);
		}
	}

	/**
	 * <p>创建列头</p>
	 * @param rowNum   行号
	 * @param selectFields
	 * @author BIG.CPU
	 */
	public void createColumnTitle(final int rowNum,final Map<String, String> selectFields) {
		this.row = this.sheet.createRow(rowNum);
		final CellStyle cellStyle = setColumnTitleStyle(selectFields);
		int headIndex = 0;
		Cell cell = null;
		final CreationHelper createHelper = workbook.getCreationHelper();
		for (final String headName : selectFields.keySet()) {
			cell = row.createCell(headIndex);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(createHelper.createRichTextString(headName));
			headIndex++;
		}
	}

	/**
	 * <p>填充业务数据</p>
	 * 
	 * @param selectFields
	 * @param filterDatas
	 * @param excelWriter
	 * @author BIG.CPU
	 */
	public void fillDatas(final Map<String, String> selectFields, final List<Map<String, String>> filterDatas,
			String[] numberCell) {
		final CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER_SELECTION);
		for (int i = 2; i < filterDatas.size() + 2; i++) {
			createRow(i);
			final Map<String, String> dataMap = filterDatas.get(i - 2);
			int colIndex = 0;
			for (final String headName : selectFields.keySet()) {
				final String fieldName = selectFields.get(headName);
				if (dataMap.containsKey(fieldName)) {
					final String value = dataMap.get(fieldName);
					setCell(colIndex, value, cellStyle);
					for (String s : numberCell) {
						if (fieldName.equals(s) && value != null && !"null".equals(value.toString())) {
							setDoubleCell(colIndex, Double.valueOf(value));
							break;
						}
					}
					colIndex++;
				}
			}
		}
	}

	/**
	 * <p>设置列头的样式</p>
	 * 
	 * @param selectFields
	 * @return
	 * @author BIG.CPU
	 */
	private CellStyle setColumnTitleStyle(final Map<String, String> selectFields) {
		final int fieldCount = selectFields.size();
		for (int i = 0; i < fieldCount; i++) {
			this.sheet.setColumnWidth(i, 6000);
		}
		row.setHeight((short) 600);
		final CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		cellStyle.setWrapText(true);

		final Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 240);
		cellStyle.setFont(font);
		return cellStyle;
	}

	public void createNormalHead(final String richTextString, final int colSum) {
		final CreationHelper createHelper = workbook.getCreationHelper();
		final Row row = this.sheet.createRow(0);

		// 设置第一行
		final Cell cell = row.createCell(0);
		row.setHeight((short) 400);

		// 定义单元格为字符串类型
		cell.setCellType(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(createHelper.createRichTextString(richTextString));

		// 指定合并区域
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, colSum));

		final CellStyle cellStyle = workbook.createCellStyle();

		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
		cellStyle.setWrapText(true);// 指定单元格自动换行

		// 设置单元格字体
		final Font font = workbook.createFont();
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontName("宋体");
		font.setFontHeight((short) 300);
		cellStyle.setFont(font);

		cell.setCellStyle(cellStyle);
	}

	/**
	 * 增加一行
	 * 
	 * @param index 行号
	 */
	public void createRow(final int index) {
		this.row = this.sheet.createRow(index);
	}

	/**
	 * 获取单元格的值
	 * 
	 * @param index 列号
	 */
	public String getCell(final int index) {
		final Cell cell = this.row.getCell((short) index);
		String strExcelCell = "";
		if (cell != null) { // add this condition
			// judge
			switch (cell.getCellType()) {
				case Cell.CELL_TYPE_FORMULA:
					strExcelCell = "FORMULA ";
					break;
				case Cell.CELL_TYPE_NUMERIC: {
					strExcelCell = String.valueOf(cell.getNumericCellValue());
				}
					break;
				case Cell.CELL_TYPE_STRING:
					strExcelCell = cell.getStringCellValue();
					break;
				case Cell.CELL_TYPE_BLANK:
					strExcelCell = "";
					break;
				default:
					strExcelCell = "";
					break;
			}
		}
		return strExcelCell;
	}

	/**
	 * 设置单元格
	 * 
	 * @param index 列号
	 * @param value 单元格填充值
	 */
	public void setCell(final int index, final int value) {
		final Cell cell = this.row.createCell((short) index);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index 列号
	 * @param value 单元格填充值
	 */
	public void setCell(final int index, final double value) {
		final Cell cell = this.row.createCell((short) index);
		cell.setCellType(Cell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		final CellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		final DataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT));// 设置cell样式为定制的浮点数格式
		cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
	}

	/**
	 * 设置单元格
	 * 
	 * @param index 列号
	 * @param value 单元格填充值
	 */
	public void setCell(final int index, final String value, final CellStyle cellStyle) {
		final Cell cell = this.row.createCell((short) index);
		cell.setCellStyle(cellStyle);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index 列号
	 * @param value 单元格填充值
	 */
	public void setDoubleCell(final int index, final Double value) {
		final Cell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		final CellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		final DataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat("#,##0.00")); // 设置cell样式为定制的浮点数格式
		cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
		sheet.setDefaultColumnStyle(index, cellStyle);
	}

	/**
	 * 设置单元格
	 * 
	 * @param index 列号
	 * @param value 单元格填充值
	 */
	public void setCell(final int index, final Calendar value) {
		final Cell cell = this.row.createCell((short) index);
		cell.setCellValue(value.getTime());
		final CellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		final DataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(DateUtils.FULL_SECOND_LINE)); // 设置cell样式为定制的日期格式
		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式
	}

	/**
	 * 
	 * <p>按照所选字段过滤查询结果</p>
	 * 
	 * @param selectFields 选择的字段
	 * @param datas 查询结果
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, String>> getFilterData(final Map<String, String> selectFields, final List<?> datas)
			throws Exception {

		final List<Map<String, String>> filterDatas = new ArrayList<Map<String, String>>();

		for (final Object result : datas) {
			final Map<String, String> filterData = new HashMap<String, String>();
			for (final String headName : selectFields.keySet()) {
				final String fieldName = selectFields.get(headName);
				final String fieldValue = getFieldValue(result, fieldName);
				filterData.put(fieldName, fieldValue);
			}
			filterDatas.add(filterData);
		}
		return filterDatas;
	}

	/**
	 * 
	 * <p>获取对应字段的值</p>
	 * 
	 * @param ins
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	private static String getFieldValue(final Object ins, final String fieldName) throws Exception {
		final BeanInfo beanInfo = Introspector.getBeanInfo(ins.getClass());
		final PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		String value = null;
		for (final PropertyDescriptor p : propertyDescriptors) {
			final Method method = p.getReadMethod();
			if (method != null) {
				final String field = p.getName();
				if (fieldName.equalsIgnoreCase(field)) {
					method.setAccessible(true);
					final Object valObj = method.invoke(ins);
					final Class<?> type = method.getReturnType();
					if (valObj != null) {
						if (type == java.util.Date.class) {
							value = FORMAT.format(valObj);
						} else if (type == java.math.BigDecimal.class) {
							final BigDecimal amount = (BigDecimal) valObj;
							value = (amount.setScale(2, RoundingMode.HALF_UP)).toString();
						} else {
							value = valObj.toString();
						}
					}
					return value;
				}
			}
		}
		return value;
	}
}
