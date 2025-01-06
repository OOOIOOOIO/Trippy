package com.sh.trippy.domain.sharechecklist.domain.model;

import com.sh.trippy.domain.common.BaseTimeEntity;
import com.sh.trippy.domain.stuff.domain.model.Stuff;
import com.sh.trippy.domain.trip.domain.model.Trip;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShareChecklist extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tripId")
    private Trip trip;

    @OneToMany(mappedBy = "shareChecklist", cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Stuff> stuffList = new ArrayList<>();


}
