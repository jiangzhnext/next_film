package com.next.jiangzh.film.dao.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
/**
 * <p>
 * 影片主表
 * </p>
 *
 * @author jiangzh
 * @since 2019-04-22
 */
public class FilmDetailT extends Model<FilmDetailT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;

    /**
     * 影片编号
     */
    private String filmId;

    /**
     * 影片英文名称
     */
    private String filmEnName;

    /**
     * 影片评分
     */
    private String filmScore;

    /**
     * 评分人数,以万为单位
     */
    private Integer filmScoreNum;

    /**
     * 播放时长，以分钟为单位，不足取整
     */
    private Integer filmLength;

    /**
     * 影片介绍
     */
    private String biography;

    /**
     * 导演编号
     */
    private Integer directorId;

    /**
     * 影片图片集地址,多个图片以逗号分隔
     */
    private String filmImgs;

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getFilmEnName() {
        return filmEnName;
    }

    public void setFilmEnName(String filmEnName) {
        this.filmEnName = filmEnName;
    }

    public String getFilmScore() {
        return filmScore;
    }

    public void setFilmScore(String filmScore) {
        this.filmScore = filmScore;
    }

    public Integer getFilmScoreNum() {
        return filmScoreNum;
    }

    public void setFilmScoreNum(Integer filmScoreNum) {
        this.filmScoreNum = filmScoreNum;
    }

    public Integer getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(Integer filmLength) {
        this.filmLength = filmLength;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public Integer getDirectorId() {
        return directorId;
    }

    public void setDirectorId(Integer directorId) {
        this.directorId = directorId;
    }

    public String getFilmImgs() {
        return filmImgs;
    }

    public void setFilmImgs(String filmImgs) {
        this.filmImgs = filmImgs;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "FilmDetailT{" +
        ", uuid=" + uuid +
        ", filmId=" + filmId +
        ", filmEnName=" + filmEnName +
        ", filmScore=" + filmScore +
        ", filmScoreNum=" + filmScoreNum +
        ", filmLength=" + filmLength +
        ", biography=" + biography +
        ", directorId=" + directorId +
        ", filmImgs=" + filmImgs +
        "}";
    }
}
