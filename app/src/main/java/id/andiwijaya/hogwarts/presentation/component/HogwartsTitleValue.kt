package id.andiwijaya.hogwarts.presentation.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import id.andiwijaya.hogwarts.R
import id.andiwijaya.hogwarts.core.Constants.ZERO
import id.andiwijaya.hogwarts.databinding.ComponentHogwartsTitleValueBinding

class HogwartsTitleValue @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleArr: Int = ZERO
) : ConstraintLayout(context, attrs, defStyleArr) {

    private val binding = ComponentHogwartsTitleValueBinding.inflate(
        LayoutInflater.from(context), this, true
    )

    init {
        context.obtainStyledAttributes(attrs, R.styleable.HogwartsTitleValue, defStyleArr, ZERO)
            .run {
                title = getString(R.styleable.HogwartsTitleValue_title).orEmpty()
                value = getString(R.styleable.HogwartsTitleValue_value).orEmpty()
                recycle()
            }
    }

    private var title: String
        set(title) {
            binding.tvTitle.text = title
        }
        get() = binding.tvTitle.text.toString()

    var value: String
        set(value) {
            binding.tvValue.text = value
        }
        get() = binding.tvValue.text.toString()

}