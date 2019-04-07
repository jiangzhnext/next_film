package com.next.jiangzh.film.dao.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
/**
 * <p>
 * 地域信息表
 * </p>
 *
 * @author jiangzh
 * @since 2019-04-06
 */
public class FilmHallDictT extends Model<FilmHallDictT> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键编号
     */
    @TableId(value = "UUID", type = IdType.AUTO)
    private Integer uuid;

    /**
     * 显示名称
     */
    private String showName;

    /**
     * 座位文件存放地址
     */
    private String seatAddress;

    public Integer getUuid() {
        return uuid;
    }

    public void setUuid(Integer uuid) {
        this.uuid = uuid;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getSeatAddress() {
        return seatAddress;
    }

    public void setSeatAddress(String seatAddress) {
        this.seatAddress = seatAddress;
    }

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "FilmHallDictT{" +
        ", uuid=" + uuid +
        ", showName=" + showName +
        ", seatAddress=" + seatAddress +
        "}";
    }
}
