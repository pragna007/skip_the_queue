package com.example.loginregisternative1;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPTableEventAfterSplit;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class TemplatePDF
{
    private  Context context;
    public Document document;
    public PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font Title = new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD, BaseColor.BLUE);
    private Font Text = new Font(Font.FontFamily.TIMES_ROMAN,10);
    private Font head = new Font(Font.FontFamily.TIMES_ROMAN,20,Font.BOLD, BaseColor.BLACK);
    public File PdfFile;


    public TemplatePDF(Context context) {
        this.context=context;
    }

    public void OpenDocument()
    {
        CreateFile();
        try {
            document=new Document(PageSize.A4);
            pdfWriter=pdfWriter.getInstance(document, new FileOutputStream(PdfFile));
        }catch (Exception e)
        {

            Log.e("Cant open document",e.toString());
        }
    }

    private void CreateFile()
    {
        File floder= new File(Environment.getExternalStorageDirectory().toString(),"SKIP-THE-QUEUE");
        if(!floder.exists())
        {
            floder.mkdir();
        }

    }

    public void closeDocument()
    {
        document.close();
    }

    public void CreateTable(String[]header, ArrayList<String[]> Names)
    {
        try
        {
            paragraph = new Paragraph();
            paragraph.setFont(Text);
            PdfPTable pdfPTable = new PdfPTable(header.length);
            pdfPTable.setTotalWidth(new float[]{20,10,10,15,10,15,10});
            pdfPTable.setWidthPercentage(100);
            PdfPCell pdfPCell;
            int indexC=0;
            while (indexC<header.length)
            {
                pdfPCell = new PdfPCell(new Phrase(header[indexC++],head));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                 pdfPTable.addCell(pdfPCell);
            }

            for(int indexR=0;indexR<Names.size();indexR++)
            {
                String[] row = Names.get(indexR);
                for (indexC = 0; indexC < header.length; indexC++)
                {
                    pdfPCell=new PdfPCell(new Phrase(row[indexC]));
                    pdfPCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    pdfPCell.setFixedHeight(40);
                    pdfPTable.addCell(pdfPCell);

                }
            }

            paragraph.add(pdfPTable);
            document.add(paragraph);
        }catch (Exception e)
        {
            Log.e("create table",e.toString());
        }
    }

    public void viewPdf()
    {
        Intent intent = new Intent(context,ViewPdfActivity.class);
        intent.putExtra("path",PdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
