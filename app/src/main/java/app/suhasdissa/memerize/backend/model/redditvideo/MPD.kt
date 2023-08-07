/*******************************************************************************
Created By Suhas Dissanayake on 8/7/23, 6:43 PM
Copyright (c) 2023
https://github.com/SuhasDissa/
All Rights Reserved
 ******************************************************************************/

package app.suhasdissa.memerize.backend.model.redditvideo

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "MPD", strict = false)
data class MPD(
    @field:Element(name = "Period")
    var period: Period? = null
)

@Root(name = "Period", strict = false)
data class Period(
    @field:ElementList(inline = true, entry = "AdaptationSet")
    var adaptationSet: ArrayList<AdaptationSet>? = arrayListOf()
)

@Root(name = "AdaptationSet", strict = false)
data class AdaptationSet(
    @field:ElementList(inline = true, entry = "Representation")
    var representationList: ArrayList<Representation>? = arrayListOf()
)

@Root(name = "Representation", strict = false)
data class Representation(
    @field:Element(name = "BaseURL")
    var baseURL: String? = null
)
