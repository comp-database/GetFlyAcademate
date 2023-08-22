package com.getfly.technologies

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import java.io.File
import java.io.FileOutputStream
import java.io.StringReader

object ExcelUtils {

    // Inside ExcelUtils.kt
    fun convertStringToExcel(context: Context, rawString: String, fileName: String) {
        val workbook: Workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Sheet 1")

        val csvParser = CSVParserBuilder().withSeparator(',').build()
        val csvReader = CSVReaderBuilder(StringReader(rawString))
            .withCSVParser(csvParser)
            .build()

        var rowNumber = 0
        var line: Array<String>?
        while (csvReader.readNext().also { line = it } != null) {
            val row = sheet.createRow(rowNumber++)
            for ((index, value) in line!!.withIndex()) {
                row.createCell(index).setCellValue(value)
            }
        }
        csvReader.close()

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "$fileName.xlsx")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
        }

        val uri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        uri?.let {
            context.contentResolver.openOutputStream(it).use { outputStream ->
                workbook.write(outputStream)
            }
        }
    }

}
