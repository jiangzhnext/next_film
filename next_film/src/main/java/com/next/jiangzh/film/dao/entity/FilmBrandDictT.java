package com.next.jiangzh.film.dao.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
/**
 * <p>
 * 品牌信息表
 * </p>
 *
 * @author jiangzh
 * @since 2019-04-06
 */
public class FilmBrandDictT extends Model<FilmBrandDictT> {

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

    @Override
    protected Serializable pkVal() {
        return null;
    }

    @Override
    public String toString() {
        return "FilmBrandDictT{" +
        ", uuid=" + uuid +
        ", showName=" + showName +
        "}";
    }
}
