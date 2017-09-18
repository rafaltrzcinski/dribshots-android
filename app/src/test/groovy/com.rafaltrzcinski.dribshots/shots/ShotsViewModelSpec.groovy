package com.rafaltrzcinski.dribshots.shots

import com.rafaltrzcinski.dribshots.rest.model.Images
import com.rafaltrzcinski.dribshots.rest.model.Shot
import com.rafaltrzcinski.dribshots.shots.list.ShotViewModel
import spock.lang.Specification

class ShotsViewModelSpec extends Specification {

    ShotViewModel viewModel

    def images = new Images("hidpi", "normal", "teaser")
    def shot = new Shot(1, "shot title", "shot description", images)

    def "setup"() {
        viewModel = new ShotViewModel(shot)
    }

    def "should get normal image as shot image"() {
        expect:
        viewModel.image == shot.images.normal
    }

    def "should get proper image title"() {
        expect:
        viewModel.title == shot.title
    }
}