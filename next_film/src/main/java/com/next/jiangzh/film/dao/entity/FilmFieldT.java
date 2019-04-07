package com.next.jiangzh.film.dao.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
/**
 * <p>
 * 放映场次表
 * </p>
 *
 * @author jiangzh
 * @since 2019-04-06
 */
public class FilmFieldT extends Model<FilmFieldT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;

    /**
     * 影院编号
     */
    private Integer cinemaId;

    /**
     * 电影编号
     */
    private Integer filmId;

    /**
     * 开始时间
     */
    private String beginTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 放映厅类型编号
     */
    private Integer hallId;

    /**
     * 放映厅名称
     */
    private String hallName;

    /**
     * 票价
     */
    private Integer price;

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public String getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getHallId() {
        return hallId;
    }

    public void setHallId(Integer hallId) {
        this.hallId = hallId;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "FilmFieldT{" +
        ", uuid=" + uuid +
        ", cinemaId=" + cinemaId +
        ", filmId=" + filmId +
        ", beginTime=" + beginTime +
        ", endTime=" + endTime +
        ", hallId=" + hallId +
        ", hallName=" + hallName +
        ", price=" + price +
        "}";
    }
}
