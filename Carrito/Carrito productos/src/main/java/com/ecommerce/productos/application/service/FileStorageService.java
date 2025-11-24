package com.ecommerce.productos.application.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    public FileStorageService(@Value("${file.upload-dir:uploads}") String uploadDir) {
        this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
            System.out.println("✅ Directorio de imágenes creado en: " + this.fileStorageLocation);
        } catch (Exception ex) {
            throw new RuntimeException("No se pudo crear el directorio de uploads", ex);
        }
    }

    /**
     * Guarda un archivo en el sistema de archivos
     * @param file archivo a guardar
     * @return nombre único del archivo guardado
     */
    public String storeFile(MultipartFile file) {
        // Validar que el archivo no esté vacío
        if (file.isEmpty()) {
            throw new RuntimeException("No se puede guardar un archivo vacío");
        }

        // Validar tipo de archivo (solo imágenes)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Solo se permiten archivos de imagen");
        }

        // Validar tamaño (máximo 5MB)
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new RuntimeException("El archivo excede el tamaño máximo de 5MB");
        }

        // Generar nombre único para el archivo
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }
        String fileName = UUID.randomUUID().toString() + fileExtension;

        try {
            // Copiar archivo al directorio
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("✅ Archivo guardado: " + fileName);
            return fileName;
        } catch (IOException ex) {
            throw new RuntimeException("Error al guardar archivo: " + fileName, ex);
        }
    }

    /**
     * Elimina un archivo del sistema de archivos
     * @param fileName nombre del archivo a eliminar
     */
    public void deleteFile(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return;
        }

        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Files.deleteIfExists(filePath);
            System.out.println("🗑️ Archivo eliminado: " + fileName);
        } catch (IOException ex) {
            System.err.println("⚠️ Error al eliminar archivo: " + fileName);
        }
    }

    /**
     * Obtiene la ruta completa de un archivo
     * @param fileName nombre del archivo
     * @return ruta completa del archivo
     */
    public Path getFilePath(String fileName) {
        return this.fileStorageLocation.resolve(fileName).normalize();
    }
}