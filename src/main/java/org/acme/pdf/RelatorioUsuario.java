package org.acme.pdf;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

@Path("/pdf")
public class RelatorioUsuario {

    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response createPdf() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(100, 700);
                contentStream.showText("Hello, Apache PDFBox!");
                contentStream.endText();
            }

            document.save(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(baos.toByteArray())
                .header("Content-Disposition", "attachment; filename=sample.pdf")
                .build();
    }

    @Path("/2")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response createPdf2() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Configurando a cor do retângulo (opcional)
                //contentStream.setNonStrokingColor(0, 128, 255); // Azul

                // Desenhando um retângulo do lado esquerdo
                contentStream.addRect(50, 650, 100, 50);
                contentStream.fill();

                // Desenhando um retângulo do lado direito
                contentStream.addRect(400, 650, 100, 50);
                contentStream.fill();

                // Mudando a cor do próximo retângulo
                //contentStream.setNonStrokingColor(255, 0, 0); // Vermelho

                // Desenhando outro retângulo do lado esquerdo (abaixo do primeiro)
                contentStream.addRect(50, 550, 100, 50);
                contentStream.fill();

                // Desenhando outro retângulo do lado direito (abaixo do primeiro)
                contentStream.addRect(400, 550, 100, 50);
                contentStream.fill();
            }

            document.save(baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Response.ok(baos.toByteArray())
                .header("Content-Disposition", "attachment; filename=rectangles.pdf")
                .build();
    }

    @Path("/3")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generatePdf() {
        try (PDDocument document = new PDDocument()) {
            // Criar uma nova página
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Obter as dimensões da página
            PDRectangle mediaBox = page.getMediaBox();
            float pageWidth = mediaBox.getWidth();
            float pageHeight = mediaBox.getHeight();


            // Definir as dimensões do retângulo
            float rectWidth = pageWidth;
            float rectHeight = 50; // Altura do retângulo
            float x = 0; // Centralizado horizontalmente
            float y = pageHeight - rectHeight; // Posicionado na parte superior

            // Iniciar o conteúdo da página
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setNonStrokingColor(0, 0, 255); // Cor azul
                contentStream.addRect(x, y, rectWidth, rectHeight);
                contentStream.fill();
            }

            // Escrever o PDF para um ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);

            // Retornar o PDF como um download
            return Response.ok(out.toByteArray())
                    .header("Content-Disposition", "attachment; filename=centralized_rectangle.pdf")
                    .build();
        } catch (IOException e) {
            return Response.serverError().entity("Erro ao gerar o PDF").build();
        }
    }

    @Path("/4")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generatePdf2() {
        try (PDDocument document = new PDDocument()) {
            // Criar uma nova página
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Obter as dimensões da página
            PDRectangle mediaBox = page.getMediaBox();
            float pageWidth = mediaBox.getWidth();
            float pageHeight = mediaBox.getHeight();

            // Definir o espaçamento
            float margin = 50; // Espaçamento ao redor do documento

            // Definir as dimensões do retângulo considerando o espaçamento
            float rectWidth = pageWidth - 2 * margin; // Largura do retângulo com o espaçamento
            float rectHeight = 50; // Altura do retângulo
            float x = margin; // Posicionar após o início do espaçamento
            float y = pageHeight - rectHeight - margin; // Posição na parte superior com espaçamento

            // Iniciar o conteúdo da página
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setNonStrokingColor(0, 0, 255); // Cor azul
                contentStream.addRect(x, y, rectWidth, rectHeight);
                contentStream.fill();
            }

            // Escrever o PDF para um ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);

            // Retornar o PDF como um download
            return Response.ok(out.toByteArray())
                    .header("Content-Disposition", "attachment; filename=centralized_rectangle_with_margin.pdf")
                    .build();
        } catch (IOException e) {
            return Response.serverError().entity("Erro ao gerar o PDF").build();
        }
    }

    @Path("/tabela")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generatePdfTabela() {
        try (PDDocument document = new PDDocument()) {
            // Criar uma nova página
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Definir as dimensões da página e margens
            PDRectangle mediaBox = page.getMediaBox();
            float margin = 50;
            float yStart = mediaBox.getHeight() - margin;

            // Definir as dimensões da tabela
            float tableWidth = mediaBox.getWidth() - 2 * margin;
            float yPosition = yStart;
            float rowHeight = 20f;
            float cellMargin = 5f;

            // Definir os dados da tabela
            String[][] content = {
                    {"Coluna 1", "Coluna 2", "Coluna 3"},
                    {"Dado 1", "Dado 2", "Dado 3"},
                    {"Dado 4", "Dado 5", "Dado 6"},
                    {"Dado 7", "Dado 8", "Dado 9"}
            };

            // Iniciar o conteúdo da página
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

                // Desenhar a tabela
                for (String[] row : content) {
                    float nextY = yPosition - rowHeight;
                    // Desenhar as células da linha
                    for (int i = 0; i < row.length; i++) {
                        float cellWidth = tableWidth / row.length;
                        float xPosition = margin + i * cellWidth;

                        // Desenhar retângulo da célula
                        contentStream.addRect(xPosition, nextY, cellWidth, rowHeight);
                        contentStream.stroke();

                        // Adicionar texto na célula
                        float textX = xPosition + cellMargin;
                        float textY = yPosition - 15;
                        contentStream.beginText();
                        contentStream.newLineAtOffset(textX, textY);
                        contentStream.showText(row[i]);
                        contentStream.endText();
                    }
                    yPosition -= rowHeight;
                }
            }

            // Escrever o PDF para um ByteArrayOutputStream
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);

            // Retornar o PDF como um download
            return Response.ok(out.toByteArray())
                    .header("Content-Disposition", "attachment; filename=table_example.pdf")
                    .build();
        } catch (IOException e) {
            return Response.serverError().entity("Erro ao gerar o PDF").build();
        }
    }

    @GET
    @Path("/sri")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response generatePdfSRI() {
        try (PDDocument document = new PDDocument()) {
            // Criar uma nova página A4
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            // Adicionar imagem no topo
            PDImageXObject topImage = PDImageXObject.createFromFile(
                    getClass().getClassLoader().getResource("img/topo.jpeg").getFile(), document);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Obter dimensões da página
            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();

            // Dimensões da imagem
            float imageWidth = topImage.getWidth();
            float imageHeight = topImage.getHeight();

            // Fator de escala para ajustar o tamanho da imagem
            float scaleFactor = 0.7f; // Reduz a imagem para 30% do tamanho original

            // Dimensões ajustadas da imagem
            float scaledWidth = imageWidth * scaleFactor;
            float scaledHeight = imageHeight * scaleFactor;

            // Calcular posição para centralizar a imagem no topo
            float x = (pageWidth - scaledWidth) / 2;
            float y = pageHeight - scaledHeight - 50; // Margem do topo de 50 unidades

            contentStream.drawImage(topImage, x, y, scaledWidth, scaledHeight);

            // Adicionar imagem no rodapé
            PDImageXObject bottomImage = PDImageXObject.createFromFile(
                    getClass().getClassLoader().getResource("img/rodape.jpeg").getFile(), document);

            imageWidth = bottomImage.getWidth();
            imageHeight = bottomImage.getHeight();

            // Dimensões ajustadas da imagem
            scaledWidth = imageWidth * scaleFactor;
            scaledHeight = imageHeight * scaleFactor;

            // Calcular posição para centralizar a imagem no rodapé
            x = (pageWidth - scaledWidth) / 2;
            y = 50; // Margem do rodapé de 50 unidades

            contentStream.drawImage(bottomImage, x, y, scaledWidth, scaledHeight);

            // Gerar QR Code
            String youtubeUrl = "https://www.youtube.com";
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(youtubeUrl, BarcodeFormat.QR_CODE, 100, 100);
            BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Salvar QR Code como imagem temporária
            File qrCodeFile = new File("qrCode.png");
            ImageIO.write(qrImage, "PNG", qrCodeFile);
            PDImageXObject qrCodeImage = PDImageXObject.createFromFile(qrCodeFile.getAbsolutePath(), document);

            // Dimensões ajustadas do QR Code
            float qrCodeWidth = qrCodeImage.getWidth() * scaleFactor;
            float qrCodeHeight = qrCodeImage.getHeight() * scaleFactor;

            // Calcular posição para o QR Code no rodapé, alinhado à direita
            float qrX = pageWidth - qrCodeWidth - 10; // Margem do lado direito de 50 unidades
            float qrY = 30; // Alinhado com a imagem de rodapé

            contentStream.drawImage(qrCodeImage, qrX, qrY, qrCodeWidth, qrCodeHeight);

            // Fechar o content stream
            contentStream.close();

            // Converter o documento em um array de bytes
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            document.save(out);

            return Response.ok(out.toByteArray())
                    .header("Content-Disposition", "attachment; filename=document_with_images.pdf")
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            return Response.serverError().build();
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }
}
