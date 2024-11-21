package com.example.qgeni.data.model

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate

data class ReadingPracticeItem(
    override val id: Int,
    override val title: String,
    val passage: String,
    override val creationDate: LocalDate,
    override val isNew: Boolean,
    val questionList: List<McqQuestion>
) : PracticeItem

object MockReadingPracticeItem {
    @RequiresApi(Build.VERSION_CODES.O)
    val readingPracticeItem = ReadingPracticeItem(
        id = 0,
        title = "Bài đọc 1",
        passage = "\tThe Benefits of Regular Exercise\n" +
    "\n" +
    " Exercise is widely recognized as a crucial component of a healthy lifestyle, offering a myriad of benefits for both physical and mental well-being. Incorporating regular exercise into one's routine can lead to numerous positive outcomes. In this article, we will explore some of the key advantages of engaging in regular physical activity.\n" +
    "\n" +
    "\tImproved Cardiovascular Health:\n" +
    " Engaging in regular exercise is known to enhance cardiovascular health by strengthening the heart and improving blood circulation. This can significantly reduce the risk of heart diseases and related issues.\n" +
    "\n" +
    "\tWeight Management:\n" +
    " Regular physical activity plays a vital role in weight management. It helps burn calories, build muscle, and maintain a healthy body weight, reducing the risk of obesity and its associated health problems.\n" +
    "\n" +
    "\tEnhanced Mental Health:\n" +
    " Exercise has a positive impact on mental health, contributing to reduced stress, anxiety, and depression. Physical activity stimulates the release of endorphins, the body's natural mood lifters.\n" +
    "\n" +
    "\tIncreased Energy Levels:\n" +
    " Contrary to common belief, regular exercise boosts energy levels. Engaging in physical activity helps improve overall endurance and stamina, making daily tasks more manageable.\n" +
    "\n" +
    "\tBetter Sleep Quality:\n" +
    " Those who engage in regular exercise often experience improved sleep quality. Physical activity helps regulate sleep patterns and promotes a more restful night's sleep.\n" +
    "\n" +
    "\tStronger Immune System:\n" +
    " Regular exercise can contribute to a stronger immune system, making the body more resilient to illnesses and infections. It enhances the production of white blood cells, which play a crucial role in immune defense.\n" +
    "\n" +
    "\tEnhanced Cognitive Function:\n" +
    " Exercise has been linked to improved cognitive function and a reduced risk of cognitive decline. It promotes better concentration, memory, and overall brain health.\n" +
    "\n" +
    "\tSocial Benefits:\n" +
    " Participating in group exercises or team sports provides social benefits, fostering a sense of community and camaraderie. This can positively impact one's mental and emotional well-being.\n" +
    "\n" +
    "\tReduced Risk of Chronic Diseases:\n" +
    " Regular physical activity is associated with a lower risk of chronic diseases such as type 2 diabetes, certain cancers, and osteoporosis. It contributes to overall health and longevity.\n" +
    "\n" +
    "\tImproved Self-Esteem:\n" +
    " Engaging in regular exercise often leads to improved self-esteem and body image. Achieving fitness goals and maintaining an active lifestyle can boost confidence and create a positive self-perception.",
        questionList = TrueFalseMockData.questions,
        creationDate = LocalDate.now(),
        isNew = false
    )

    @RequiresApi(Build.VERSION_CODES.O)
    val readingPracticeItemList = List(8) { index ->
        ReadingPracticeItem(
            id = index,
            title = "Bài đọc ${index + 1}",
            creationDate = LocalDate.now().minusDays(index.toLong()),
            isNew = index < 3,
            passage = "\tThe Benefits of Regular Exercise\n" +
                    "\n" +
                    " Exercise is widely recognized as a crucial component of a healthy lifestyle, offering a myriad of benefits for both physical and mental well-being. Incorporating regular exercise into one's routine can lead to numerous positive outcomes. In this article, we will explore some of the key advantages of engaging in regular physical activity.\n" +
                    "\n" +
                    "\tImproved Cardiovascular Health:\n" +
                    " Engaging in regular exercise is known to enhance cardiovascular health by strengthening the heart and improving blood circulation. This can significantly reduce the risk of heart diseases and related issues.\n" +
                    "\n" +
                    "\tWeight Management:\n" +
                    " Regular physical activity plays a vital role in weight management. It helps burn calories, build muscle, and maintain a healthy body weight, reducing the risk of obesity and its associated health problems.\n" +
                    "\n" +
                    "\tEnhanced Mental Health:\n" +
                    " Exercise has a positive impact on mental health, contributing to reduced stress, anxiety, and depression. Physical activity stimulates the release of endorphins, the body's natural mood lifters.\n" +
                    "\n" +
                    "\tIncreased Energy Levels:\n" +
                    " Contrary to common belief, regular exercise boosts energy levels. Engaging in physical activity helps improve overall endurance and stamina, making daily tasks more manageable.\n" +
                    "\n" +
                    "\tBetter Sleep Quality:\n" +
                    " Those who engage in regular exercise often experience improved sleep quality. Physical activity helps regulate sleep patterns and promotes a more restful night's sleep.\n" +
                    "\n" +
                    "\tStronger Immune System:\n" +
                    " Regular exercise can contribute to a stronger immune system, making the body more resilient to illnesses and infections. It enhances the production of white blood cells, which play a crucial role in immune defense.\n" +
                    "\n" +
                    "\tEnhanced Cognitive Function:\n" +
                    " Exercise has been linked to improved cognitive function and a reduced risk of cognitive decline. It promotes better concentration, memory, and overall brain health.\n" +
                    "\n" +
                    "\tSocial Benefits:\n" +
                    " Participating in group exercises or team sports provides social benefits, fostering a sense of community and camaraderie. This can positively impact one's mental and emotional well-being.\n" +
                    "\n" +
                    "\tReduced Risk of Chronic Diseases:\n" +
                    " Regular physical activity is associated with a lower risk of chronic diseases such as type 2 diabetes, certain cancers, and osteoporosis. It contributes to overall health and longevity.\n" +
                    "\n" +
                    "\tImproved Self-Esteem:\n" +
                    " Engaging in regular exercise often leads to improved self-esteem and body image. Achieving fitness goals and maintaining an active lifestyle can boost confidence and create a positive self-perception.",
            questionList = TrueFalseMockData.questions,
        )
    }
}
