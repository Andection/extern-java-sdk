package ru.kontur.extern_api.sdk.model;

import ru.kontur.extern_api.sdk.model.DocumentTypes.Fns534Report;

/**
 * <p>
 * Класс содержит данные, описывающие документ, после отправки.
 * Используется в сервисе {@code DraftService}
 * </p>
 * @author Andrey Manakov
 */
public class DocflowDocumentDescription {

    private Fns534Report type = null;
    private String filename = null;
    private String contentType = null;
    private Boolean compressed = null;
    public DocflowDocumentDescription type(Fns534Report type) {
        this.type = type;
        return this;
    }

    /**
     * Возвращает информацию о том сжат ли зашифрованный контент.
     * @return compressed
     */
    public Boolean getCompressed() {
        return compressed;
    }

    /**
     * Устанавливает сжатый ли зашифрованный контент.
     * @param compressed сжатый ли зашифрованный контент
     */
    public void setCompressed(Boolean compressed) {
        this.compressed = compressed;
    }

    /**
     * Возвращает тип документа. Могут быть следующие типы документов для отправки:
     * <ul>
     *   <li>urn:document:fns534-report - декларация для ФНС</li>
     *   <li>urn:document:fns534-report-warrant - доверенность</li>
     *   <li>urn:document:fns534-report-attachment - приложение к декларации для ФНС</li>
     * </ul>
     * @return type
     */
    public Fns534Report getType() {
        return type;
    }

    /**
     * Устанавливает тип документа. Могут быть следующие типы документов для отправки:
     * @param type тип документа
     * <ul>
     *   <li>urn:document:fns534-report - декларация для ФНС</li>
     *   <li>urn:document:fns534-report-warrant - доверенность</li>
     *   <li>urn:document:fns534-report-attachment - приложение к декларации для ФНС</li>
     * </ul>
     */
    public void setType(Fns534Report type) {
        this.type = type;
    }

    /**
     * Устанавливает имя файла декларации.
     * Метод пытается угадать тип отправляемого документа по его имени файла
     * @param filename имя файла декларации
     * @return {@link DocumentDescription}
     */
    public DocflowDocumentDescription filename(String filename) {
        setFilename(filename);
        return this;
    }

    /**
     * Возвращает имя файла декларации
     * @return filename имя файла декларации
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Устанавливает имя файла декларации.
     * Метод пытается угадать тип отправляемого документа по его имени файла
     * @param filename имя файла декларации
     */
    public void setFilename(String filename) {
        this.filename = filename;
        // пытаемся угадать тип
        // this.type = Type.guessType(filename).value;
    }

    /**
     * Устанавливает тип контента (content-type) документа
     * @param contentType тип контента (content-type) документа
     * @return {@link DocumentDescription}
     */
    public DocflowDocumentDescription contentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    /**
     * Возвращает тип контента (content-type) документа
     * @return contentType тип контента (content-type) документа
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * Устанавливает тип контента (content-type) документа
     * @param contentType тип контента (content-type) документа
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
