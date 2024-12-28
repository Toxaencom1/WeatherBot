package com.taxah.weathersenderproject.model.weatherEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.taxah.weathersenderproject.model.enums.WeatherCondition;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "hourly_data")
public class HourlyData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime date;
    private String weather;
    private int icon;

    @Enumerated(EnumType.STRING)
    private WeatherCondition summary;

    private double temperature;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wind_id")
    private Wind wind;

    @JsonProperty("cloud_cover")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cloud_cover_id")
    private CloudCover cloudCover;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "precipitation_id")
    private Precipitation precipitation;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        HourlyData that = (HourlyData) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
